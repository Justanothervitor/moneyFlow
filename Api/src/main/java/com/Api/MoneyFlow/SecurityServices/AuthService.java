package com.Api.MoneyFlow.SecurityServices;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.Api.MoneyFlow.MainCfg.JwtCfg.JwtUtils;
import com.Api.MoneyFlow.Domains.UserDomain;

public interface AuthService extends UserDetailsService{

	Authentication getCurrentAuthentication();
	UserDetailsImpl getCurrentUser();
    UserDomain getCurrentLoggedUser(String token);
	
	
}
