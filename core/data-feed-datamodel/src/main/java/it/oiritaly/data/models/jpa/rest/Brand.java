package it.oiritaly.data.models.jpa.rest;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Brand {

    @Id
    private Long _id;

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToMany(mappedBy = "brands")
    private List<Rule> rules;

}
