package com.justanothervitor.api_2.models;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name="Users")
public class User implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String email;
    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name="Authentication",joinColumns = @JoinColumn(name="userid"),inverseJoinColumns = @JoinColumn(columnDefinition = "roleid"))
    private Set<Role> roles = new HashSet<>();
    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name="UserNotes",joinColumns = @JoinColumn(name = "userid"), inverseJoinColumns = @JoinColumn(columnDefinition = "noteid"))
    private ArrayList<Notes> notes = new ArrayList<>();
    private boolean enabled;
    @Enumerated(EnumType.STRING)
    private AuthProvider provider = AuthProvider.LOCAL;
    private String providerId;
    @Column(name="emailVerified")
    private boolean emailVerified = false;

}
