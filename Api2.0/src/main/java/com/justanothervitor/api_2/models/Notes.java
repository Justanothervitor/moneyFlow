package com.justanothervitor.api_2.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity(name = "notes")
@NoArgsConstructor
public class Notes implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name="description")
    private String description;
    @Column(name="value")
    private BigDecimal price;
    @Column(name = "dateofcreation")
    private LocalDateTime dateOfCreation;
    @Column(name="dateofupdate")
    private LocalDateTime dateOfUpdate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userid",nullable = false)
    private User author;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "tags",
            joinColumns = @JoinColumn(name = "noteid"),
            inverseJoinColumns = @JoinColumn(name = "categoryid"))
    private Set<Category> tags = new HashSet<>();

    public Notes(final String name,final String description, final BigDecimal price, final User author, final Set<Category> tags){
        this.name = name;
        this.description = description;
        this.price = price;
        this.author = author;
        this.dateOfCreation = LocalDateTime.now();
        this.dateOfUpdate = LocalDateTime.now();
        this.tags = tags;
    }


}
