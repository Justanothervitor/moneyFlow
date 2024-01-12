package com.justAnotherVitor.MoneyFlow.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import com.justAnotherVitor.MoneyFlow.config.jwt.AuthEntryPointJwt;
import com.justAnotherVitor.MoneyFlow.config.jwt.AuthTokenFilter;

@Configuration
@EnableMethodSecurity
public class WebSecurityConfig
{
	@Autowired
	UserDetailsService userDetailsService;
	
	@Autowired
	private AuthEntryPointJwt unauthorizedHandler;
	
	  public AuthTokenFilter authenticationJwtTokenFilter() {
		    return new AuthTokenFilter();
		  }

	  public DaoAuthenticationProvider authenticationProvider() {
	    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

	    authProvider.setUserDetailsService(userDetailsService);
	    authProvider.setPasswordEncoder(passwordEncoder());

	    return authProvider;
	  }
	  	
	  @Bean
	  public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
	    return authConfig.getAuthenticationManager();
	  }
	  
	  @Bean
	  public PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	  }
	  
	  @Bean
	    SecurityFilterChain appSecurity(HttpSecurity http, MvcRequestMatcher.Builder mvc) throws Exception {

	       http.csrf(csrf->csrf.disable())
	    		   .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
	    		   .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
	    		   .authorizeHttpRequests(auth->auth.requestMatchers(mvc.pattern("api/auth/**")).permitAll()
	    		   .anyRequest().permitAll()
	    		   /*.requestMatchers(mvc.pattern("api/user/***")).authenticated()
	    		   .requestMatchers(mvc.pattern("api/test/**")).permitAll()
	    		   .anyRequest().permitAll()*/
	    			);
	       return http.build();
	    }

	    @Bean
	    MvcRequestMatcher.Builder mvc(HandlerMappingIntrospector introspector) {
	        return new MvcRequestMatcher.Builder(introspector);
	    }
	}