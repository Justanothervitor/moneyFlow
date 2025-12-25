package com.justanothervitor.api_2.services;

import com.justanothervitor.api_2.config.auth.JwtUtil;
import com.justanothervitor.api_2.models.AuthProvider;
import com.justanothervitor.api_2.models.Role;
import com.justanothervitor.api_2.models.User;
import com.justanothervitor.api_2.models.payloads.request.CreateUserPayload;
import com.justanothervitor.api_2.models.payloads.request.LoginPayload;
import com.justanothervitor.api_2.models.payloads.response.SucessfullAuthResponse;
import com.justanothervitor.api_2.repositories.RoleRepositories;
import com.justanothervitor.api_2.repositories.UserRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthService {

    protected UserRepositories userRepositories;
    protected PasswordEncoder passwordEncoder;
    protected RoleRepositories roleRepositories;
    protected AuthenticationManager authenticationManager;
    protected JwtUtil jwtUtils;

    @Autowired
    public AuthService(UserRepositories userRepositories, PasswordEncoder passwordEncoder,RoleRepositories roleRepositories) {
        this.userRepositories = userRepositories;
        this.passwordEncoder = passwordEncoder;
        this.roleRepositories = roleRepositories;
    }

    public User register(CreateUserPayload createUserPayload) {
        if(userRepositories.existsByUsername(createUserPayload.getUsername())){
            throw new RuntimeException("Username already exists");
        }
        if(userRepositories.existsByEmail(createUserPayload.getEmail())){
            throw new RuntimeException("Email already exists");
        }
       Set<Role> roles  = new HashSet<>();
        roles.add(roleRepositories.findRoleByName("NORMAL"));

        User user = new User();
        user.setUsername(createUserPayload.getUsername());
        user.setEmail(createUserPayload.getEmail());
        user.setPassword(passwordEncoder.encode(createUserPayload.getPassword()));
        user.setRoles(roles);
        user.setEnabled(true);
        user.setProvider(AuthProvider.LOCAL);

        return userRepositories.save(user);
    }


    public SucessfullAuthResponse login(LoginPayload request)
    {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt =  jwtUtils.generateToken(request.getUsername());
        User authenticated = userRepositories.findByUsername(request.getUsername()).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        return new SucessfullAuthResponse(
                "Bearer "+jwt,
                authenticated.getUsername(),
                authenticated.getEmail()
        );
    }
}
