/*******************************************************************************
 *  Copyright (c) 2016 Dr.-Ing. Marc Mültin.
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 *
 *  Contributors:
 *    Dr.-Ing. Marc Mültin - initial API and implementation and initial documentation
 *******************************************************************************/
package org.eclipse.risev2g.evcc.transportLayer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Inet6Address;
import java.util.Arrays;
import java.util.Observable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.risev2g.shared.messageHandling.TerminateSession;
import org.eclipse.risev2g.shared.misc.V2GTPMessage;
import org.eclipse.risev2g.shared.utils.ByteUtils;
import org.eclipse.risev2g.shared.utils.MiscUtils;

public abstract class StatefulTransportLayerClient  extends Observable implements Runnable {

	private Logger logger = LogManager.getLogger(this.getClass().getSimpleName());
	private byte[] v2gTPHeader; 
	private byte[] v2gTPPayload;
	private byte[] v2gTPMessage;
	private InputStream inStream;
	private OutputStream outStream;
	private final int MASK = 0x80;
	private int payloadLength;
	private int bytesReadFromInputStream;
	private Inet6Address clientAddress;
	private int clientPort;
	private int timeout;
	private boolean stopAlreadyInitiated;
	
	protected void initialize() {
		getLogger().debug("Initializing client connection ...");
		setClientPort(MiscUtils.getRandomPortNumber());
		setClientAddress(MiscUtils.getLinkLocalAddress());
		setV2gTPHeader(new byte[8]);
	}
	
	protected boolean processIncomingMessage() throws IOException {
		/*
		 * Read header (8 bytes) of incoming V2GTPMessage to further allocate a byte array with  
		 * the appropriate length. 
		 */
		try {
			setBytesReadFromInputStream(getInStream().read(getV2gTPHeader()));
		} catch (IOException e) {
			
		}
	
		if (getBytesReadFromInputStream() < 0) {
			stopAndNotify("No bytes read from input stream, server socket seems to be closed", null);
			return false;
		}
	
		/*
		 * The payload length is written to the last 4 bytes (v2gTPHeader[4] to v2gTPHeader[7])
		 * of the V2GTP header. The most significant bit of v2gTPHeader[4] should never be set!
		 * If it was set, then this would mean that a V2GTP message of a size of at least 2 GB 
		 * was intended to be transferred ... and this cannot be, no V2G message has this size!
		 * Since the most significant bit should never be set, we do not need to care about
		 * signed integers in Java at this point!
		 */
		if ((getV2gTPHeader()[4] & getMASK()) == getMASK()) {
			stopAndNotify("Payload length of V2GTP message is inappropiately high! There must be " +
						  "an error in the V2GTP message header!", null);
			return false;
		} else {
			setPayloadLength(ByteUtils.toIntFromByteArray(Arrays.copyOfRange(getV2gTPHeader(), 4, 8)));
			setV2gTPPayload(new byte[getPayloadLength()]);
		
			getInStream().read(getV2gTPPayload());
		
			getLogger().debug("Message received");
		
			setV2gTPMessage(new byte[getV2gTPHeader().length + getV2gTPPayload().length]);
			System.arraycopy(getV2gTPHeader(), 0, getV2gTPMessage(), 0, getV2gTPHeader().length);
			System.arraycopy(getV2gTPPayload(), 0, getV2gTPMessage(), getV2gTPHeader().length, getV2gTPPayload().length);
		}
	
		// Block another while-run before the new Socket timeout has been provided by send()
		// TODO is there a more elegant way of blocking (this is rather resource-consuming)?
		setTimeout(-1); 
	
		setChanged();
		notifyObservers(getV2gTPMessage());
		
		return true;
	}
	
	public abstract void send(V2GTPMessage message, int timeout);
	
	/**
	 * If an error occurred in the run()-method, the TCP client will be stopped by closing all streams
	 * and the socket and interrupting the Thread. V2GCommunicationSessionEVCC will be notified as well.
	 * The method's statements will not be executed if a stop of the TCP client has already been
	 * initiated by the V2GCommunicationSessionEVCC (which might induce an error in the run()-method).
	 * 
	 * @param errorMessage An error message explaining the reason for the error
	 * @param e An optional exception
	 */
	protected void stopAndNotify(String errorMessage, Exception e) {
		if (!isStopAlreadyInitiated()) {
			getLogger().error(errorMessage, e);
			stop();
			setStopAlreadyInitiated(true);
			
			// Notify V2GCommunicationSessionEVCC about termination of session
			setChanged();
			notifyObservers(new TerminateSession(errorMessage));
		}
	}
	

	public abstract void stop();
	
	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}
	
	public byte[] getV2gTPHeader() {
		return v2gTPHeader;
	}

	public void setV2gTPHeader(byte[] v2gTPHeader) {
		this.v2gTPHeader = v2gTPHeader;
	}

	public byte[] getV2gTPPayload() {
		return v2gTPPayload;
	}

	public void setV2gTPPayload(byte[] v2gTPPayload) {
		this.v2gTPPayload = v2gTPPayload;
	}

	public byte[] getV2gTPMessage() {
		return v2gTPMessage;
	}

	public void setV2gTPMessage(byte[] v2gTPMessage) {
		this.v2gTPMessage = v2gTPMessage;
	}
	
	public InputStream getInStream() {
		return inStream;
	}

	public void setInStream(InputStream inStream) {
		this.inStream = inStream;
	}

	public OutputStream getOutStream() {
		return outStream;
	}

	public void setOutStream(OutputStream outStream) {
		this.outStream = outStream;
	}

	public int getPayloadLength() {
		return payloadLength;
	}

	public void setPayloadLength(int payloadLength) {
		this.payloadLength = payloadLength;
	}

	public int getBytesReadFromInputStream() {
		return bytesReadFromInputStream;
	}

	public void setBytesReadFromInputStream(int bytesReadFromInputStream) {
		this.bytesReadFromInputStream = bytesReadFromInputStream;
	}

	public int getMASK() {
		return MASK;
	}
	
	public Inet6Address getClientAddress() {
		return clientAddress;
	}

	public void setClientAddress(Inet6Address clientAddress) {
		this.clientAddress = clientAddress;
	}

	public int getClientPort() {
		return clientPort;
	}

	public void setClientPort(int clientPort) {
		this.clientPort = clientPort;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public boolean isStopAlreadyInitiated() {
		return stopAlreadyInitiated;
	}

	public void setStopAlreadyInitiated(boolean stopAlreadyInitiated) {
		this.stopAlreadyInitiated = stopAlreadyInitiated;
	}
	
}
