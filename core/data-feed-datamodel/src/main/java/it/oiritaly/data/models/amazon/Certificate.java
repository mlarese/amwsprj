//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.05.03 at 03:15:27 PM EDT 
//


package it.oiritaly.data.models.amazon;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="certificate_type" type="{}StringValue"/>
 *         &lt;element name="certificate_number" type="{}StringValue"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "certificateType",
    "certificateNumber"
})
@XmlRootElement(name = "Certificate")
public class Certificate {

    @XmlElement(name = "certificate_type", required = true)
    protected StringValue certificateType;
    @XmlElement(name = "certificate_number", required = true)
    protected StringValue certificateNumber;

    /**
     * Gets the value of the certificateType property.
     * 
     * @return
     *     possible object is
     *     {@link StringValue }
     *     
     */
    public StringValue getCertificateType() {
        return certificateType;
    }

    /**
     * Sets the value of the certificateType property.
     * 
     * @param value
     *     allowed object is
     *     {@link StringValue }
     *     
     */
    public void setCertificateType(StringValue value) {
        this.certificateType = value;
    }

    /**
     * Gets the value of the certificateNumber property.
     * 
     * @return
     *     possible object is
     *     {@link StringValue }
     *     
     */
    public StringValue getCertificateNumber() {
        return certificateNumber;
    }

    /**
     * Sets the value of the certificateNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link StringValue }
     *     
     */
    public void setCertificateNumber(StringValue value) {
        this.certificateNumber = value;
    }

}