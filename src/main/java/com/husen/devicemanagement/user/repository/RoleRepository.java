package com.husen.devicemanagement.user.repository;

import com.husen.devicemanagement.user.model.RoleMaster;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<RoleMaster, Long> {

}
