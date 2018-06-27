package it.oiritaly.data.models.xml;

import java.net.URI;

import lombok.Data;

@Data
public class ImageLink {
    private URI link;
    private String label;
}
