package it.oiritaly.data.models.jpa;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.time.LocalDateTime;

@Data
@Entity
public class DataFeed {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long _id;

    //@Column(nullable = false)
    //private Date parseDate;

    @Temporal(TemporalType.TIMESTAMP)
    @org.hibernate.annotations.ColumnDefault("CURRENT_TIMESTAMP")
    private Date parseDateDateTime;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, optional = false)
    private Root root;
}
