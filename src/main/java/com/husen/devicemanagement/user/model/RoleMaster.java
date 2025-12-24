package com.husen.devicemanagement.user.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "roles")
public class RoleMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<PermissionMaster> permissions = new HashSet<>();

    protected RoleMaster() {

    }

    public RoleMaster(String name, Set<PermissionMaster> permissions) {
        this.name = name;
        this.permissions = permissions;
    }
}

