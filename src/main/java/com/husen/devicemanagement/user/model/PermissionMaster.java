package com.husen.devicemanagement.user.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "permissions")
public class PermissionMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    protected PermissionMaster() {
    }

    public PermissionMaster(String name) {
        this.name = name;
    }
}

