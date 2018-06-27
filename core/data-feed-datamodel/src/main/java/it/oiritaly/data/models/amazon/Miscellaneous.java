//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.05.03 at 03:15:27 PM EDT 
//


package it.oiritaly.data.models.amazon;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.NormalizedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.XMLGregorianCalendar;


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
 *         &lt;element name="ProductType" type="{}MiscType"/>
 *         &lt;element name="ProductCategory" type="{}MiscType" minOccurs="0"/>
 *         &lt;element name="ProductSubcategory" type="{}MiscSubtype" minOccurs="0"/>
 *         &lt;element name="Manufacturer" type="{}FortyStringNotNull" minOccurs="0"/>
 *         &lt;element name="Keywords" type="{}LongStringNotNull" maxOccurs="5" minOccurs="0"/>
 *         &lt;element name="Color" type="{}StringNotNull" minOccurs="0"/>
 *         &lt;element name="Size" type="{}FortyStringNotNull" minOccurs="0"/>
 *         &lt;element name="EventDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="ModelName" type="{}FortyStringNotNull" minOccurs="0"/>
 *         &lt;element name="ModelNumber" type="{}FortyStringNotNull" minOccurs="0"/>
 *         &lt;element name="MfrPartNumber" type="{}FortyStringNotNull" minOccurs="0"/>
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
    "productType",
    "productCategory",
    "productSubcategory",
    "manufacturer",
    "keywords",
    "color",
    "size",
    "eventDate",
    "modelName",
    "modelNumber",
    "mfrPartNumber"
})
@XmlRootElement(name = "Miscellaneous")
public class Miscellaneous {

    @XmlElement(name = "ProductType", required = true)
    protected MiscType productType;
    @XmlElement(name = "ProductCategory")
    protected MiscType productCategory;
    @XmlElement(name = "ProductSubcategory")
    protected MiscSubtype productSubcategory;
    @XmlElement(name = "Manufacturer")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String manufacturer;
    @XmlElement(name = "Keywords")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected List<String> keywords;
    @XmlElement(name = "Color")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String color;
    @XmlElement(name = "Size")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String size;
    @XmlElement(name = "EventDate")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar eventDate;
    @XmlElement(name = "ModelName")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String modelName;
    @XmlElement(name = "ModelNumber")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String modelNumber;
    @XmlElement(name = "MfrPartNumber")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String mfrPartNumber;

    /**
     * Gets the value of the productType property.
     * 
     * @return
     *     possible object is
     *     {@link MiscType }
     *     
     */
    public MiscType getProductType() {
        return productType;
    }

    /**
     * Sets the value of the productType property.
     * 
     * @param value
     *     allowed object is
     *     {@link MiscType }
     *     
     */
    public void setProductType(MiscType value) {
        this.productType = value;
    }

    /**
     * Gets the value of the productCategory property.
     * 
     * @return
     *     possible object is
     *     {@link MiscType }
     *     
     */
    public MiscType getProductCategory() {
        return productCategory;
    }

    /**
     * Sets the value of the productCategory property.
     * 
     * @param value
     *     allowed object is
     *     {@link MiscType }
     *     
     */
    public void setProductCategory(MiscType value) {
        this.productCategory = value;
    }

    /**
     * Gets the value of the productSubcategory property.
     * 
     * @return
     *     possible object is
     *     {@link MiscSubtype }
     *     
     */
    public MiscSubtype getProductSubcategory() {
        return productSubcategory;
    }

    /**
     * Sets the value of the productSubcategory property.
     * 
     * @param value
     *     allowed object is
     *     {@link MiscSubtype }
     *     
     */
    public void setProductSubcategory(MiscSubtype value) {
        this.productSubcategory = value;
    }

    /**
     * Gets the value of the manufacturer property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getManufacturer() {
        return manufacturer;
    }

    /**
     * Sets the value of the manufacturer property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setManufacturer(String value) {
        this.manufacturer = value;
    }

    /**
     * Gets the value of the keywords property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the keywords property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getKeywords().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getKeywords() {
        if (keywords == null) {
            keywords = new ArrayList<String>();
        }
        return this.keywords;
    }

    /**
     * Gets the value of the color property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getColor() {
        return color;
    }

    /**
     * Sets the value of the color property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setColor(String value) {
        this.color = value;
    }

    /**
     * Gets the value of the size property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSize() {
        return size;
    }

    /**
     * Sets the value of the size property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSize(String value) {
        this.size = value;
    }

    /**
     * Gets the value of the eventDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getEventDate() {
        return eventDate;
    }

    /**
     * Sets the value of the eventDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setEventDate(XMLGregorianCalendar value) {
        this.eventDate = value;
    }

    /**
     * Gets the value of the modelName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModelName() {
        return modelName;
    }

    /**
     * Sets the value of the modelName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModelName(String value) {
        this.modelName = value;
    }

    /**
     * Gets the value of the modelNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModelNumber() {
        return modelNumber;
    }

    /**
     * Sets the value of the modelNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModelNumber(String value) {
        this.modelNumber = value;
    }

    /**
     * Gets the value of the mfrPartNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMfrPartNumber() {
        return mfrPartNumber;
    }

    /**
     * Sets the value of the mfrPartNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMfrPartNumber(String value) {
        this.mfrPartNumber = value;
    }

}
