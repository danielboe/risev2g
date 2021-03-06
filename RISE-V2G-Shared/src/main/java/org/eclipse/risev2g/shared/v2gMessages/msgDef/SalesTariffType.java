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
//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 generiert 
// Siehe <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2014.10.07 um 04:55:05 PM CEST 
//


package org.eclipse.risev2g.shared.v2gMessages.msgDef;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java-Klasse für SalesTariffType complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="SalesTariffType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="SalesTariffID" type="{urn:iso:15118:2:2013:MsgDataTypes}SAIDType"/>
 *         &lt;element name="SalesTariffDescription" type="{urn:iso:15118:2:2013:MsgDataTypes}tariffDescriptionType" minOccurs="0"/>
 *         &lt;element name="NumEPriceLevels" type="{http://www.w3.org/2001/XMLSchema}unsignedByte" minOccurs="0"/>
 *         &lt;element ref="{urn:iso:15118:2:2013:MsgDataTypes}SalesTariffEntry" maxOccurs="1024"/>
 *       &lt;/sequence>
 *       &lt;attribute name="Id" type="{http://www.w3.org/2001/XMLSchema}ID" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SalesTariffType", propOrder = {
    "salesTariffID",
    "salesTariffDescription",
    "numEPriceLevels",
    "salesTariffEntry"
})
public class SalesTariffType {

    @XmlElement(name = "SalesTariffID")
    @XmlSchemaType(name = "unsignedByte")
    protected short salesTariffID;
    @XmlElement(name = "SalesTariffDescription")
    protected String salesTariffDescription;
    @XmlElement(name = "NumEPriceLevels")
    @XmlSchemaType(name = "unsignedByte")
    protected Short numEPriceLevels;
    @XmlElement(name = "SalesTariffEntry", required = true)
    protected List<SalesTariffEntryType> salesTariffEntry;
    @XmlAttribute(name = "Id", namespace = "urn:iso:15118:2:2013:MsgDataTypes")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    @XmlSchemaType(name = "ID")
    protected String id;

    /**
     * Ruft den Wert der salesTariffID-Eigenschaft ab.
     * 
     */
    public short getSalesTariffID() {
        return salesTariffID;
    }

    /**
     * Legt den Wert der salesTariffID-Eigenschaft fest.
     * 
     */
    public void setSalesTariffID(short value) {
        this.salesTariffID = value;
    }

    /**
     * Ruft den Wert der salesTariffDescription-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSalesTariffDescription() {
        return salesTariffDescription;
    }

    /**
     * Legt den Wert der salesTariffDescription-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSalesTariffDescription(String value) {
        this.salesTariffDescription = value;
    }

    /**
     * Ruft den Wert der numEPriceLevels-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Short }
     *     
     */
    public Short getNumEPriceLevels() {
        return numEPriceLevels;
    }

    /**
     * Legt den Wert der numEPriceLevels-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Short }
     *     
     */
    public void setNumEPriceLevels(Short value) {
        this.numEPriceLevels = value;
    }

    /**
     * Gets the value of the salesTariffEntry property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the salesTariffEntry property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSalesTariffEntry().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SalesTariffEntryType }
     * 
     * 
     */
    public List<SalesTariffEntryType> getSalesTariffEntry() {
        if (salesTariffEntry == null) {
            salesTariffEntry = new ArrayList<SalesTariffEntryType>();
        }
        return this.salesTariffEntry;
    }

    /**
     * Ruft den Wert der id-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Legt den Wert der id-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

}
