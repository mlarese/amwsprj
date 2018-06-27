//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.05.03 at 03:15:27 PM EDT 
//


package it.oiritaly.data.models.amazon;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.NormalizedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for CustomerAddress complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CustomerAddress">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Name">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="150"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="AddressFieldOne">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}normalizedString">
 *               &lt;maxLength value="180"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="AddressFieldTwo" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}normalizedString">
 *               &lt;maxLength value="180"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="AddressFieldThree" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}normalizedString">
 *               &lt;maxLength value="180"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="City">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="150"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="StateOrRegion" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="150"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="PostalCode" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="20"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="CountryCode">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;minLength value="2"/>
 *               &lt;maxLength value="2"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="PhoneNumber" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="20"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *       &lt;/sequence>
 *       &lt;attribute name="isBilling" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="isShipping" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="isDefaultShipping" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="MerchantAddressID" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CustomerAddress", propOrder = {
    "name",
    "addressFieldOne",
    "addressFieldTwo",
    "addressFieldThree",
    "city",
    "stateOrRegion",
    "postalCode",
    "countryCode",
    "phoneNumber"
})
public class CustomerAddress {

    @XmlElement(name = "Name", required = true)
    protected String name;
    @XmlElement(name = "AddressFieldOne", required = true)
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String addressFieldOne;
    @XmlElement(name = "AddressFieldTwo")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String addressFieldTwo;
    @XmlElement(name = "AddressFieldThree")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String addressFieldThree;
    @XmlElement(name = "City", required = true)
    protected String city;
    @XmlElement(name = "StateOrRegion")
    protected String stateOrRegion;
    @XmlElement(name = "PostalCode")
    protected String postalCode;
    @XmlElement(name = "CountryCode", required = true)
    protected String countryCode;
    @XmlElement(name = "PhoneNumber")
    protected String phoneNumber;
    @XmlAttribute(name = "isBilling")
    protected Boolean isBilling;
    @XmlAttribute(name = "isShipping")
    protected Boolean isShipping;
    @XmlAttribute(name = "isDefaultShipping")
    protected Boolean isDefaultShipping;
    @XmlAttribute(name = "MerchantAddressID")
    @XmlSchemaType(name = "anySimpleType")
    protected String merchantAddressID;

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the addressFieldOne property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddressFieldOne() {
        return addressFieldOne;
    }

    /**
     * Sets the value of the addressFieldOne property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddressFieldOne(String value) {
        this.addressFieldOne = value;
    }

    /**
     * Gets the value of the addressFieldTwo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddressFieldTwo() {
        return addressFieldTwo;
    }

    /**
     * Sets the value of the addressFieldTwo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddressFieldTwo(String value) {
        this.addressFieldTwo = value;
    }

    /**
     * Gets the value of the addressFieldThree property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddressFieldThree() {
        return addressFieldThree;
    }

    /**
     * Sets the value of the addressFieldThree property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddressFieldThree(String value) {
        this.addressFieldThree = value;
    }

    /**
     * Gets the value of the city property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets the value of the city property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCity(String value) {
        this.city = value;
    }

    /**
     * Gets the value of the stateOrRegion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStateOrRegion() {
        return stateOrRegion;
    }

    /**
     * Sets the value of the stateOrRegion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStateOrRegion(String value) {
        this.stateOrRegion = value;
    }

    /**
     * Gets the value of the postalCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * Sets the value of the postalCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPostalCode(String value) {
        this.postalCode = value;
    }

    /**
     * Gets the value of the countryCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCountryCode() {
        return countryCode;
    }

    /**
     * Sets the value of the countryCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCountryCode(String value) {
        this.countryCode = value;
    }

    /**
     * Gets the value of the phoneNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets the value of the phoneNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPhoneNumber(String value) {
        this.phoneNumber = value;
    }

    /**
     * Gets the value of the isBilling property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsBilling() {
        return isBilling;
    }

    /**
     * Sets the value of the isBilling property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsBilling(Boolean value) {
        this.isBilling = value;
    }

    /**
     * Gets the value of the isShipping property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsShipping() {
        return isShipping;
    }

    /**
     * Sets the value of the isShipping property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsShipping(Boolean value) {
        this.isShipping = value;
    }

    /**
     * Gets the value of the isDefaultShipping property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsDefaultShipping() {
        return isDefaultShipping;
    }

    /**
     * Sets the value of the isDefaultShipping property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsDefaultShipping(Boolean value) {
        this.isDefaultShipping = value;
    }

    /**
     * Gets the value of the merchantAddressID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMerchantAddressID() {
        return merchantAddressID;
    }

    /**
     * Sets the value of the merchantAddressID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMerchantAddressID(String value) {
        this.merchantAddressID = value;
    }

}
