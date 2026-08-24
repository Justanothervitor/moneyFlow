package com.justanothervitor.api_2.models;

import com.justanothervitor.api_2.models.Enums.AuthProvider;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity(name = "users")
public class User implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name="email")
    private String email;
    @Column(name="enabled")
    private boolean enabled;
    @Enumerated(EnumType.STRING)
    private AuthProvider provider = AuthProvider.LOCAL;
    @Column(name = "providerId")
    private String providerId;
    @Column(name="emailVerified")
    private boolean emailVerified = false;
    @Column(name="twofactor")
    private boolean twofactor = false;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name="authentication",joinColumns = @JoinColumn(name="userid"),inverseJoinColumns = @JoinColumn(name = "roleid"))
    private Set<Role> roles = new HashSet<>();
    
    @OneToMany(mappedBy = "author",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<Notes> notes = new ArrayList<>();

    public User() {}

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }
}
