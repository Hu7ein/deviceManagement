package com.husen.devicemanagement.user.service;

import com.husen.devicemanagement.user.model.PermissionMaster;
import com.husen.devicemanagement.user.model.RoleMaster;
import com.husen.devicemanagement.user.model.UserMaster;
import com.husen.devicemanagement.user.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {

        UserMaster user = userRepository.findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        List<String> authorities =
                user.getRoles().stream()
                        .flatMap(role -> {
                            Stream<String> roleAuthority =
                                    Stream.of("ROLE_" + role.getName());

                            Stream<String> permissionAuthorities =
                                    role.getPermissions().stream()
                                            .map(PermissionMaster::getName);

                            return Stream.concat(roleAuthority, permissionAuthorities);
                        })
                        .toList();

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUserName())
                .password(user.getPassword())
                .authorities(authorities.toArray(String[]::new))
                .build();
    }

}

