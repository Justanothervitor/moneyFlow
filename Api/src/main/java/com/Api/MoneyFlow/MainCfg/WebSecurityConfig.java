package com.Api.MoneyFlow.MainCfg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.Api.MoneyFlow.MainCfg.JwtCfg.AuthEntryPoint;
import com.Api.MoneyFlow.MainCfg.JwtCfg.AuthTokenFilter;
import com.Api.MoneyFlow.MainCfg.JwtCfg.JwtUtils;
import com.Api.MoneyFlow.SecurityServices.AuthServiceImpl;

@Configuration
@EnableMethodSecurity
public class WebSecurityConfig {

	@Autowired
	protected UserDetailsService userDetailsService;
	
	@Autowired
	protected AuthEntryPoint unauthorizedHandler;
	
	@Autowired
	protected JwtUtils jwtUtils;
	
	@Autowired
	protected AuthServiceImpl authService;
	
	public AuthTokenFilter authenticationJwtTokenFilter() {
		return new AuthTokenFilter(jwtUtils,authService);
	}
	
	public DaoAuthenticationProvider authenticationProvider()
	{
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		
		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(passwordEnconder());
		
		return authProvider;
	}
	
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception{
		return authConfig.getAuthenticationManager();
	}
	
	@Bean
	PasswordEncoder passwordEnconder()
	{
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	SecurityFilterChain appSecurity(HttpSecurity http) throws Exception{
		
		http
		.csrf(AbstractHttpConfigurer::disable)
		.exceptionHandling(exception-> exception.authenticationEntryPoint(unauthorizedHandler))
		.sessionManagement(session-> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		.authorizeHttpRequests(auth -> auth.requestMatchers("/api/auth/**").permitAll()
				.requestMatchers("/api/data/**").permitAll()
				.requestMatchers("api/home/**").permitAll().anyRequest().authenticated());
		http.authenticationProvider(authenticationProvider());
		http.addFilterBefore(authenticationJwtTokenFilter(),UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}
	
	
}
