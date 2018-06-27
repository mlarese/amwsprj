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
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AdjustmentBuyerPrice complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AdjustmentBuyerPrice">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Component" maxOccurs="unbounded">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Type">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         &lt;enumeration value="Principal"/>
 *                         &lt;enumeration value="Shipping"/>
 *                         &lt;enumeration value="Tax"/>
 *                         &lt;enumeration value="ShippingTax"/>
 *                         &lt;enumeration value="RestockingFee"/>
 *                         &lt;enumeration value="RestockingFeeTax"/>
 *                         &lt;enumeration value="GiftWrap"/>
 *                         &lt;enumeration value="GiftWrapTax"/>
 *                         &lt;enumeration value="Surcharge"/>
 *                         &lt;enumeration value="ReturnShipping"/>
 *                         &lt;enumeration value="Goodwill"/>
 *                         &lt;enumeration value="ExportCharge"/>
 *                         &lt;enumeration value="COD"/>
 *                         &lt;enumeration value="CODTax"/>
 *                         &lt;enumeration value="Other"/>
 *                         &lt;enumeration value="FreeReplacementReturnShipping"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="Amount" type="{}AdjustmentCurrencyAmount"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AdjustmentBuyerPrice", propOrder = {
    "component"
})
public class AdjustmentBuyerPrice {

    @XmlElement(name = "Component", required = true)
    protected List<AdjustmentBuyerPrice.Component> component;

    /**
     * Gets the value of the component property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the component property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getComponent().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AdjustmentBuyerPrice.Component }
     * 
     * 
     */
    public List<AdjustmentBuyerPrice.Component> getComponent() {
        if (component == null) {
            component = new ArrayList<AdjustmentBuyerPrice.Component>();
        }
        return this.component;
    }


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
     *         &lt;element name="Type">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;enumeration value="Principal"/>
     *               &lt;enumeration value="Shipping"/>
     *               &lt;enumeration value="Tax"/>
     *               &lt;enumeration value="ShippingTax"/>
     *               &lt;enumeration value="RestockingFee"/>
     *               &lt;enumeration value="RestockingFeeTax"/>
     *               &lt;enumeration value="GiftWrap"/>
     *               &lt;enumeration value="GiftWrapTax"/>
     *               &lt;enumeration value="Surcharge"/>
     *               &lt;enumeration value="ReturnShipping"/>
     *               &lt;enumeration value="Goodwill"/>
     *               &lt;enumeration value="ExportCharge"/>
     *               &lt;enumeration value="COD"/>
     *               &lt;enumeration value="CODTax"/>
     *               &lt;enumeration value="Other"/>
     *               &lt;enumeration value="FreeReplacementReturnShipping"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="Amount" type="{}AdjustmentCurrencyAmount"/>
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
        "type",
        "amount"
    })
    public static class Component {

        @XmlElement(name = "Type", required = true)
        protected String type;
        @XmlElement(name = "Amount", required = true)
        protected AdjustmentCurrencyAmount amount;

        /**
         * Gets the value of the type property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getType() {
            return type;
        }

        /**
         * Sets the value of the type property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setType(String value) {
            this.type = value;
        }

        /**
         * Gets the value of the amount property.
         * 
         * @return
         *     possible object is
         *     {@link AdjustmentCurrencyAmount }
         *     
         */
        public AdjustmentCurrencyAmount getAmount() {
            return amount;
        }

        /**
         * Sets the value of the amount property.
         * 
         * @param value
         *     allowed object is
         *     {@link AdjustmentCurrencyAmount }
         *     
         */
        public void setAmount(AdjustmentCurrencyAmount value) {
            this.amount = value;
        }

    }

}
