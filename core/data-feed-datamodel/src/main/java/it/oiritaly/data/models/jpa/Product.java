package it.oiritaly.data.models.jpa;

import java.net.URI;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.Data;

@Data
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long _id;

    @Column(nullable = false)
    private String id;
    @Column(nullable = false)
    private String title;
    @Lob
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private URI link;
    @Column(nullable = false)
    private String contentLanguage;
    @Column(nullable = false)
    private String targetCountry;
    @Column()
    private String ageGroup;
    @Column(nullable = false)
    private String availability;
    @Column(nullable = false)
    private String brand;
    @Column(nullable = false)
    private String condition;
    @Column()
    private Long gtin;
    @Column(nullable = false)
    private String mpn;
    @Column(nullable = false)
    private Long quantity;
    @Column(nullable = false)
    private Boolean delete;
    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    private Category category;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, optional = false)
    private Price price;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, optional = false)
    private Price salePrice;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ImageLink> imageLinks;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CustomAttribute> customAttributes;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Output> output;
    @Column()
    private Boolean isGroupParent;
    @Column()
    private String itemGroupId;
}
