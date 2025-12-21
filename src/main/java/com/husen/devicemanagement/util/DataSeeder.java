package com.husen.devicemanagement.util;

import com.husen.devicemanagement.user.model.PermissionMaster;
import com.husen.devicemanagement.user.model.RoleMaster;
import com.husen.devicemanagement.user.model.UserMaster;
import com.husen.devicemanagement.user.repository.PermissionRepository;
import com.husen.devicemanagement.user.repository.RoleRepository;
import com.husen.devicemanagement.user.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class DataSeeder implements CommandLineRunner {

    private final RoleRepository roleRepo;
    private final PermissionRepository permissionRepo;
    private final UserRepository userRepo;
    private final PasswordEncoder encoder;

    public DataSeeder(RoleRepository roleRepo,
                      PermissionRepository permissionRepo,
                      UserRepository userRepo,
                      PasswordEncoder encoder) {
        this.roleRepo = roleRepo;
        this.permissionRepo = permissionRepo;
        this.userRepo = userRepo;
        this.encoder = encoder;
    }

    @Override
    public void run(String[] args) {

        PermissionMaster read = permissionRepo.save(new PermissionMaster("DEVICE_READ"));
        PermissionMaster create = permissionRepo.save(new PermissionMaster("DEVICE_CREATE"));
        PermissionMaster update = permissionRepo.save(new PermissionMaster("DEVICE_UPDATE"));
        PermissionMaster delete = permissionRepo.save(new PermissionMaster("DEVICE_DELETE"));
        PermissionMaster generate = permissionRepo.save(new PermissionMaster("DEVICE_GENERATE"));

        RoleMaster admin = new RoleMaster("ADMIN",
                Set.of(read, create, update, delete, generate));

        RoleMaster user = new RoleMaster("USER",
                Set.of(read, create, update, delete));

        roleRepo.saveAll(List.of(admin, user));

        UserMaster adminUser = new UserMaster(
                "admin",
                encoder.encode("admin123"),
                Set.of(admin)
        );

        UserMaster normalUser = new UserMaster(
                "user",
                encoder.encode("user123"),
                Set.of(user)
        );

        userRepo.saveAll(List.of(adminUser,normalUser));
    }
}


