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
import javax.xml.bind.annotation.XmlAttribute;
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
 *         &lt;element name="value" maxOccurs="5" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="australia_toy_fair_toy_of_the_year"/>
 *               &lt;enumeration value="australia_toy_fair_boys_toy_of_the_year"/>
 *               &lt;enumeration value="child_magazine"/>
 *               &lt;enumeration value="dr_toys_100_best_child_products"/>
 *               &lt;enumeration value="energizer_battery_operated_toy_of_the_yr"/>
 *               &lt;enumeration value="family_fun_toy_of_the_year_seal"/>
 *               &lt;enumeration value="games_magazine"/>
 *               &lt;enumeration value="german_toy_association_toy_of_the_year"/>
 *               &lt;enumeration value="hamleys_toy_of_the_year"/>
 *               &lt;enumeration value="lion_mark"/>
 *               &lt;enumeration value="national_parenting_approval_award"/>
 *               &lt;enumeration value="norwegian_toy_association_toy_of_the_yr"/>
 *               &lt;enumeration value="oppenheim_toys"/>
 *               &lt;enumeration value="parents_choice_portfolio"/>
 *               &lt;enumeration value="parents_magazine"/>
 *               &lt;enumeration value="rdj_france_best_electronic_toy_of_the_yr"/>
 *               &lt;enumeration value="rdj_france_best_toy_of_the_year"/>
 *               &lt;enumeration value="toy_wishes"/>
 *               &lt;enumeration value="uk_npd_report_number_one_selling_toy"/>
 *               &lt;enumeration value="unknown"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
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
@XmlRootElement(name = "toy_award")
public class ToyAward {

    protected List<String> value;
    @XmlAttribute(name = "delete")
    protected BooleanType delete;

    /**
     * Gets the value of the value property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the value property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getValue().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getValue() {
        if (value == null) {
            value = new ArrayList<String>();
        }
        return this.value;
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

}
