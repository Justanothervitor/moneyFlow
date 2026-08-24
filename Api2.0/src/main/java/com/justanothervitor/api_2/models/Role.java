package com.justanothervitor.api_2.models;

import com.justanothervitor.api_2.models.Enums.ERole;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity(name = "roles")
public class Role{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    private String name;
    @Enumerated(EnumType.STRING)
    @Column(name = "erole")
    private ERole ERole;

}
