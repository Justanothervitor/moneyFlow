package com.justanothervitor.api_2.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "Categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;

    @Enumerated(EnumType.STRING)
    private EnumTag tag;
}
