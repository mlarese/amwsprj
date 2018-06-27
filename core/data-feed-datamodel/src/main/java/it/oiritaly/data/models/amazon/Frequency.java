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
 * <p>Java class for Frequency.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="Frequency">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="microhertz"/>
 *     &lt;enumeration value="millihertz"/>
 *     &lt;enumeration value="hertz"/>
 *     &lt;enumeration value="kilohertz"/>
 *     &lt;enumeration value="megahertz"/>
 *     &lt;enumeration value="gigahertz"/>
 *     &lt;enumeration value="terahertz"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "Frequency")
@XmlEnum
public enum Frequency {

    @XmlEnumValue("microhertz")
    MICROHERTZ("microhertz"),
    @XmlEnumValue("millihertz")
    MILLIHERTZ("millihertz"),
    @XmlEnumValue("hertz")
    HERTZ("hertz"),
    @XmlEnumValue("kilohertz")
    KILOHERTZ("kilohertz"),
    @XmlEnumValue("megahertz")
    MEGAHERTZ("megahertz"),
    @XmlEnumValue("gigahertz")
    GIGAHERTZ("gigahertz"),
    @XmlEnumValue("terahertz")
    TERAHERTZ("terahertz");
    private final String value;

    Frequency(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static Frequency fromValue(String v) {
        for (Frequency c: Frequency.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
