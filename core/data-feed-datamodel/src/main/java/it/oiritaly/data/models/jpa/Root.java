package it.oiritaly.data.models.jpa;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Root {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long _id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String subtitle;
    @Column(nullable = false)
    private String id;
    @Column(nullable = false)
    private String rights;
    @Column(nullable = false)
    private String updated;
    @ManyToOne(cascade = CascadeType.ALL)
    private Author author;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch=FetchType.EAGER)
    private List<Product> products;
}
