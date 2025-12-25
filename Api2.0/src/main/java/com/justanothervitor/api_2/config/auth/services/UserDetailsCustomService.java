package com.justanothervitor.api_2.config.auth.services;

import com.justanothervitor.api_2.models.User;
import com.justanothervitor.api_2.repositories.UserRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsCustomService implements UserDetailsService {

    protected UserRepositories userRepositories;

    @Autowired
    public UserDetailsCustomService(UserRepositories userRepositories) {
        this.userRepositories = userRepositories;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepositories.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));

        return org.springframework.security.core.userdetails.User
                .withUsername(username).password(user.getPassword()).roles(user.getRoles().stream().findFirst().toString()).build();
    }

    public UserDetails loadUserById(Long id)
    {
        User user = userRepositories.findById(id).orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        return org.springframework.security.core.userdetails.User.withUsername(user.getUsername())
                .password(user.getPassword() != null ? user.getPassword() : "")
                .roles(user.getRoles().stream().findFirst().toString())
                .disabled(!user.isEnabled())
                .build();

    }
}
