package it.oiritaly.data.models.jpa.rest;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class Rule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long _id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String marketplaceId;

    @Column(nullable = false)
    private Date fromDate;

    @Column(nullable = false)
    private Date toDate;

    @Column
    private Boolean isActive;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "Brand_Rule",
            joinColumns = @JoinColumn(name = "brand__id", referencedColumnName = "_id"),
            inverseJoinColumns = @JoinColumn(name = "rule__id",
                    referencedColumnName = "_id"))
    private List<Brand> brands;
}
