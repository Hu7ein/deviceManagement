package com.husen.devicemanagement.user.repository;

import com.husen.devicemanagement.user.model.PermissionMaster;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<PermissionMaster, Long> {
}
