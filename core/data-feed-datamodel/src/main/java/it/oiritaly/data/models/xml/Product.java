package it.oiritaly.data.models.xml;

import java.net.URI;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Data;

@Data
@XmlRootElement(name = "product")
@XmlAccessorType(XmlAccessType.FIELD)
public class Product {
    private String id;
    private String title;
    private String description;
    private URI link;
    private String content_language;
    private String target_country;
    private String age_group;
    private String availability;
    private String brand;
    private String condition;
    private Long gtin;
    private String mpn;
    private Long quantity;
    private Boolean delete;
    private Boolean is_group_parent;
    private String item_group_id;

    @XmlElement(type = Price.class)
    private Price price;
    @XmlElement(type = Category.class)
    private Category category;
    @XmlElement(name = "sale_price", type = Price.class)
    private Price salePrice;
    @XmlElement(name = "image_link", type = ImageLink.class)
    private List<ImageLink> imageLinks;
    @XmlElement(name = "custom_attribute", type = CustomAttribute.class)
    private List<CustomAttribute> customAttributes;
    @XmlElement(name = "output", type = String.class)
    private List<String> output;
}
