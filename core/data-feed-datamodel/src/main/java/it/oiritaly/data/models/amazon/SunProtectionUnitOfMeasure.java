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
 * <p>Java class for SunProtectionUnitOfMeasure.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="SunProtectionUnitOfMeasure">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="sun_protection_factor"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "SunProtectionUnitOfMeasure")
@XmlEnum
public enum SunProtectionUnitOfMeasure {

    @XmlEnumValue("sun_protection_factor")
    SUN_PROTECTION_FACTOR("sun_protection_factor");
    private final String value;

    SunProtectionUnitOfMeasure(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static SunProtectionUnitOfMeasure fromValue(String v) {
        for (SunProtectionUnitOfMeasure c: SunProtectionUnitOfMeasure.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
