package com.justanothervitor.api_2.models;

import com.justanothervitor.api_2.models.Enums.EnumTag;
import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "etag")
    private EnumTag tag;
}
