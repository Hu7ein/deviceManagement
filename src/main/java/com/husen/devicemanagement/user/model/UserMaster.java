package com.husen.devicemanagement.user.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
public class UserMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String userName;

    @Column(nullable = false)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<RoleMaster> roles = new HashSet<>();

    public UserMaster(String userName, String password, Set<RoleMaster> roles) {
        this.userName = userName;
        this.password = password;
        this.roles = roles;
    }

    protected UserMaster() {
    }

}

