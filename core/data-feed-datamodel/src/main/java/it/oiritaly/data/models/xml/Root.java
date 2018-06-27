package it.oiritaly.data.models.xml;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Data;

@Data
@XmlRootElement(name = "root")
@XmlAccessorType(XmlAccessType.FIELD)
public class Root {
    private String id;
    private String title;
    private String subtitle;
    private String rights;
    private String updated;
    @XmlElement(name = "author", type = Author.class)
    private Author author;
    @XmlElement(name = "product", type = Product.class)
    private List<Product> products;
}
