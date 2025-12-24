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
    @JoinTable(
            name = "roles_permissions",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    private Set<PermissionMaster> permissions = new HashSet<>();

    @ManyToMany(mappedBy = "roles")
    private Set<UserMaster> users = new HashSet<>();

    protected RoleMaster() {}

    public RoleMaster(String name, Set<PermissionMaster> permissions) {
        this.name = name;
        this.permissions = permissions;
    }
}
