package com.husen.devicemanagement.auth.controller;

import com.husen.devicemanagement.auth.dto.JwtResponse;
import com.husen.devicemanagement.auth.dto.LoginRequest;
import com.husen.devicemanagement.auth.security.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;

    public AuthController(AuthenticationManager authManager, JwtUtil jwtUtil) {
        this.authManager = authManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public JwtResponse login(@RequestBody LoginRequest request) {

        Authentication authentication =
                authManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.username(),
                                request.password()
                        )
                );

        UserDetails user = (UserDetails) authentication.getPrincipal();
        String token = jwtUtil.generateToken(user);

        return new JwtResponse(token);
    }
}

