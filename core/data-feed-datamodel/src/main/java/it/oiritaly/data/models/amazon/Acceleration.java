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
 * <p>Java class for Acceleration.
 * <p>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="Acceleration">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="gravity"/>
 *     &lt;enumeration value="inches_per_second_squared"/>
 *     &lt;enumeration value="feet_per_second_squared"/>
 *     &lt;enumeration value="meters_per_second_squared"/>
 *     &lt;enumeration value="radians_per_second_squared"/>
 *     &lt;enumeration value="degrees_per_second_squared"/>
 *     &lt;enumeration value="revolutions_per_second_squared"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 */
@XmlType(name = "Acceleration")
@XmlEnum
public enum Acceleration {

    @XmlEnumValue("gravity")
    GRAVITY("gravity"),
    @XmlEnumValue("inches_per_second_squared")
    INCHES_PER_SECOND_SQUARED("inches_per_second_squared"),
    @XmlEnumValue("feet_per_second_squared")
    FEET_PER_SECOND_SQUARED("feet_per_second_squared"),
    @XmlEnumValue("meters_per_second_squared")
    METERS_PER_SECOND_SQUARED("meters_per_second_squared"),
    @XmlEnumValue("radians_per_second_squared")
    RADIANS_PER_SECOND_SQUARED("radians_per_second_squared"),
    @XmlEnumValue("degrees_per_second_squared")
    DEGREES_PER_SECOND_SQUARED("degrees_per_second_squared"),
    @XmlEnumValue("revolutions_per_second_squared")
    REVOLUTIONS_PER_SECOND_SQUARED("revolutions_per_second_squared");
    private final String value;

    Acceleration(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static Acceleration fromValue(String v) {
        for (Acceleration c : Acceleration.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
