//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.05.03 at 03:15:27 PM EDT 
//


package it.oiritaly.data.models.amazon;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;


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
 *         &lt;element name="value">
 *           &lt;complexType>
 *             &lt;simpleContent>
 *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>decimal">
 *                 &lt;attribute name="unitValue" use="required" type="{}Voltage" />
 *               &lt;/extension>
 *             &lt;/simpleContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *       &lt;attribute name="delete" type="{}BooleanType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "value"
})
@XmlRootElement(name = "pickup_voltage")
public class PickupVoltage {

    @XmlElement(required = true)
    protected PickupVoltage.Value value;
    @XmlAttribute(name = "delete")
    protected BooleanType delete;

    /**
     * Gets the value of the value property.
     * 
     * @return
     *     possible object is
     *     {@link PickupVoltage.Value }
     *     
     */
    public PickupVoltage.Value getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     * 
     * @param value
     *     allowed object is
     *     {@link PickupVoltage.Value }
     *     
     */
    public void setValue(PickupVoltage.Value value) {
        this.value = value;
    }

    /**
     * Gets the value of the delete property.
     * 
     * @return
     *     possible object is
     *     {@link BooleanType }
     *     
     */
    public BooleanType getDelete() {
        return delete;
    }

    /**
     * Sets the value of the delete property.
     * 
     * @param value
     *     allowed object is
     *     {@link BooleanType }
     *     
     */
    public void setDelete(BooleanType value) {
        this.delete = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;simpleContent>
     *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>decimal">
     *       &lt;attribute name="unitValue" use="required" type="{}Voltage" />
     *     &lt;/extension>
     *   &lt;/simpleContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "value"
    })
    public static class Value {

        @XmlValue
        protected BigDecimal value;
        @XmlAttribute(name = "unitValue", required = true)
        protected Voltage unitValue;

        /**
         * Gets the value of the value property.
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getValue() {
            return value;
        }

        /**
         * Sets the value of the value property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setValue(BigDecimal value) {
            this.value = value;
        }

        /**
         * Gets the value of the unitValue property.
         * 
         * @return
         *     possible object is
         *     {@link Voltage }
         *     
         */
        public Voltage getUnitValue() {
            return unitValue;
        }

        /**
         * Sets the value of the unitValue property.
         * 
         * @param value
         *     allowed object is
         *     {@link Voltage }
         *     
         */
        public void setUnitValue(Voltage value) {
            this.unitValue = value;
        }

    }

}
