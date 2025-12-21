package com.husen.devicemanagement.user.repository;

import com.husen.devicemanagement.user.model.UserMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserMaster, Long> {
    Optional<UserMaster> findByUserName(String username);
}

