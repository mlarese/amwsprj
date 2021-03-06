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
 *         &lt;element name="value" maxOccurs="unbounded" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="3do"/>
 *               &lt;enumeration value="atari_2600"/>
 *               &lt;enumeration value="atari_jaguar"/>
 *               &lt;enumeration value="atari_st"/>
 *               &lt;enumeration value="be_os"/>
 *               &lt;enumeration value="colecovision"/>
 *               &lt;enumeration value="cybiko"/>
 *               &lt;enumeration value="dos"/>
 *               &lt;enumeration value="dreamcast"/>
 *               &lt;enumeration value="electronic_games"/>
 *               &lt;enumeration value="epoc"/>
 *               &lt;enumeration value="game_boy_advance"/>
 *               &lt;enumeration value="gameboy"/>
 *               &lt;enumeration value="gameboy_color"/>
 *               &lt;enumeration value="gamecube"/>
 *               &lt;enumeration value="linux"/>
 *               &lt;enumeration value="mac_os_9_0_and_below"/>
 *               &lt;enumeration value="mac_os_x"/>
 *               &lt;enumeration value="mac_os_x_cheetah"/>
 *               &lt;enumeration value="mac_os_x_intel"/>
 *               &lt;enumeration value="mac_os_x_jaguar"/>
 *               &lt;enumeration value="mac_os_x_leopard"/>
 *               &lt;enumeration value="mac_os_x_panther"/>
 *               &lt;enumeration value="mac_os_x_puma"/>
 *               &lt;enumeration value="mac_os_x_tiger"/>
 *               &lt;enumeration value="macintosh"/>
 *               &lt;enumeration value="microsoft_xbox_360"/>
 *               &lt;enumeration value="microsoft_xp_media_center"/>
 *               &lt;enumeration value="n_gage"/>
 *               &lt;enumeration value="neo_geo"/>
 *               &lt;enumeration value="neo_geo_pocket"/>
 *               &lt;enumeration value="netware"/>
 *               &lt;enumeration value="nintendo_NES"/>
 *               &lt;enumeration value="nintendo_super_NES"/>
 *               &lt;enumeration value="nintendo64"/>
 *               &lt;enumeration value="no_operating_system"/>
 *               &lt;enumeration value="not_machine_specific"/>
 *               &lt;enumeration value="os/2"/>
 *               &lt;enumeration value="palm"/>
 *               &lt;enumeration value="pda"/>
 *               &lt;enumeration value="playstation"/>
 *               &lt;enumeration value="playstation_2"/>
 *               &lt;enumeration value="pocket_pc"/>
 *               &lt;enumeration value="pocket_pc_2002"/>
 *               &lt;enumeration value="pocket_pc_2003"/>
 *               &lt;enumeration value="powermac"/>
 *               &lt;enumeration value="sega_game_gear"/>
 *               &lt;enumeration value="sega_genesis"/>
 *               &lt;enumeration value="sega_master_system"/>
 *               &lt;enumeration value="sega_mega_cd"/>
 *               &lt;enumeration value="sega_saturn"/>
 *               &lt;enumeration value="sony_playstation3"/>
 *               &lt;enumeration value="sony_psp"/>
 *               &lt;enumeration value="sun_solaris"/>
 *               &lt;enumeration value="super_nintendo"/>
 *               &lt;enumeration value="trs_80"/>
 *               &lt;enumeration value="unix"/>
 *               &lt;enumeration value="virtual_boy"/>
 *               &lt;enumeration value="windows"/>
 *               &lt;enumeration value="windows_2000_server"/>
 *               &lt;enumeration value="windows_2003_server"/>
 *               &lt;enumeration value="windows_mobile"/>
 *               &lt;enumeration value="windows_mobile_2003"/>
 *               &lt;enumeration value="windows_mobile_5"/>
 *               &lt;enumeration value="windows_vista"/>
 *               &lt;enumeration value="windows_vista_business"/>
 *               &lt;enumeration value="windows_vista_enterprise"/>
 *               &lt;enumeration value="windows_vista_home_basic"/>
 *               &lt;enumeration value="windows_vista_home_premium"/>
 *               &lt;enumeration value="windows_vista_ultimate"/>
 *               &lt;enumeration value="windows_xp"/>
 *               &lt;enumeration value="windows_xp_home"/>
 *               &lt;enumeration value="windows_xp_professional"/>
 *               &lt;enumeration value="windows_xp_tablet_pc"/>
 *               &lt;enumeration value="windows2000"/>
 *               &lt;enumeration value="windows3_1"/>
 *               &lt;enumeration value="windows3_x"/>
 *               &lt;enumeration value="windows95"/>
 *               &lt;enumeration value="windows98"/>
 *               &lt;enumeration value="windowsCE"/>
 *               &lt;enumeration value="windowsME"/>
 *               &lt;enumeration value="windowsNT"/>
 *               &lt;enumeration value="windowsNT3_5"/>
 *               &lt;enumeration value="windowsNT4"/>
 *               &lt;enumeration value="windowsNT5"/>
 *               &lt;enumeration value="wonderswan"/>
 *               &lt;enumeration value="wonderswan_color"/>
 *               &lt;enumeration value="xbox"/>
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
@XmlRootElement(name = "platform")
public class Platform {

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
