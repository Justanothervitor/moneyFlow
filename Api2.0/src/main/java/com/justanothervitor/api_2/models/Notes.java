package com.justanothervitor.api_2.models;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "Notes")
public class Notes implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String description;
    private float price;
    private Timestamp dateOfCreation;
    private Timestamp dateOfUpdate;

    @ManyToOne(fetch = FetchType.LAZY)
    private User author;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name="Tags",joinColumns = @JoinColumn(name="annotationid"),inverseJoinColumns = @JoinColumn(columnDefinition= "categoryid"))
    private Set<Category> Tags = new HashSet<>();

}
