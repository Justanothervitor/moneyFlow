package com.justanothervitor.api_2.services;

import com.justanothervitor.api_2.config.auth.JwtUtil;
import com.justanothervitor.api_2.exceptions.InvalidAccountException;
import com.justanothervitor.api_2.exceptions.NotFoundException;
import com.justanothervitor.api_2.models.Enums.AuthProvider;
import com.justanothervitor.api_2.models.Enums.VerificationType;
import com.justanothervitor.api_2.models.Role;
import com.justanothervitor.api_2.models.User;
import com.justanothervitor.api_2.models.payloads.request.CreateUserPayload;
import com.justanothervitor.api_2.models.payloads.request.LoginPayload;
import com.justanothervitor.api_2.models.payloads.response.SuccessfulAuthResponse;
import com.justanothervitor.api_2.models.payloads.response.VerificationResponse;
import com.justanothervitor.api_2.repositories.RoleRepositories;
import com.justanothervitor.api_2.repositories.UserRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Service
public class AuthService {

    protected UserRepositories userRepositories;
    protected PasswordEncoder passwordEncoder;
    protected RoleRepositories roleRepositories;
    protected AuthenticationManager authenticationManager;
    protected JwtUtil jwtUtils;
    protected VerificationService verificationService;

    @Autowired
    public AuthService(UserRepositories userRepositories, PasswordEncoder passwordEncoder,RoleRepositories roleRepositories,AuthenticationManager authenticationManager, JwtUtil jwtUtils) {
        this.userRepositories = userRepositories;
        this.passwordEncoder = passwordEncoder;
        this.roleRepositories = roleRepositories;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
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
        user.setNotes(new ArrayList<>());
        user.setProvider(AuthProvider.LOCAL);
        userRepositories.save(user);
        return user;
    }


    public SuccessfulAuthResponse login(LoginPayload request)
    {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassword()));
        System.out.println(authentication.getName());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt =  jwtUtils.generateToken(request.getUsername());
        User authenticated = userRepositories.findByUsername(request.getUsername()).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        return new SuccessfulAuthResponse(
                "Bearer "+jwt,
                authenticated.getUsername(),
                authenticated.getEmail()
        );
    }

   /* public VerificationResponse forgotPasword(String email) throws NotFoundException, InvalidAccountException {
            User user = userRepositories.findByEmail(email).orElseThrow(()-> new NotFoundException("Usuário não encontrado!"));
            if(user.getProvider() != AuthProvider.LOCAL){
                throw new InvalidAccountException("A conta foi criada usando métodos da Google e ou do Facebook, por favor use os outros métodos!");
            }
        return this.verificationService.generateAndSendCode(user, VerificationType.PASSWORD_RESET);
    }*/

}
