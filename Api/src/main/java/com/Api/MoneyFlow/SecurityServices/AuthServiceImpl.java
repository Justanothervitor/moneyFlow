package com.Api.MoneyFlow.SecurityServices;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.Api.MoneyFlow.MainCfg.JwtCfg.JwtUtils;
import com.Api.MoneyFlow.Repositories.UserRepositories;
import com.Api.MoneyFlow.Domains.UserDomain;

@Service
public class AuthServiceImpl implements AuthService {

    protected UserRepositories userRepo;
    protected JwtUtils jwtUtils;


    public AuthServiceImpl(UserRepositories userRepo, JwtUtils jwtUtils) {
        this.userRepo = userRepo;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDomain user = this.userRepo.findByUsername(username);
        if (user == null) {
            throw (new UsernameNotFoundException("User not found with username:" + username));
        }
        return new UserDetailsImpl(user);
    }

    @Override
    public Authentication getCurrentAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @Override
    public UserDetailsImpl getCurrentUser() {
        return (UserDetailsImpl) getCurrentAuthentication().getPrincipal();
    }

    @Override
    public UserDomain getCurrentLoggedUser(String token) {
        return this.userRepo.findByUsername(this.jwtUtils.getUsernameFromJwtToken(token));
    }
}