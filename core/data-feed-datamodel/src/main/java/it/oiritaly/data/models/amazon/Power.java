//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.05.03 at 03:15:27 PM EDT 
//


package it.oiritaly.data.models.amazon;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Power.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="Power">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="milliwatts"/>
 *     &lt;enumeration value="microwatts"/>
 *     &lt;enumeration value="nanowatts"/>
 *     &lt;enumeration value="picowatts"/>
 *     &lt;enumeration value="watts"/>
 *     &lt;enumeration value="horsepower"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "Power")
@XmlEnum
public enum Power {

    @XmlEnumValue("milliwatts")
    MILLIWATTS("milliwatts"),
    @XmlEnumValue("microwatts")
    MICROWATTS("microwatts"),
    @XmlEnumValue("nanowatts")
    NANOWATTS("nanowatts"),
    @XmlEnumValue("picowatts")
    PICOWATTS("picowatts"),
    @XmlEnumValue("watts")
    WATTS("watts"),
    @XmlEnumValue("horsepower")
    HORSEPOWER("horsepower");
    private final String value;

    Power(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static Power fromValue(String v) {
        for (Power c: Power.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
