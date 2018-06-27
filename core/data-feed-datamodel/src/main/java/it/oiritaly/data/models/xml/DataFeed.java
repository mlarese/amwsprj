package it.oiritaly.data.models.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Data;

@Data
@XmlRootElement(name = "datafeed")
@XmlAccessorType(XmlAccessType.FIELD)
public class DataFeed {
    @XmlElement(name = "root", type = Root.class)
    private Root root;
}
