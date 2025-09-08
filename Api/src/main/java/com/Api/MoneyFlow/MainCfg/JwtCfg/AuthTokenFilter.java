package com.Api.MoneyFlow.MainCfg.JwtCfg;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNullApi;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.Api.MoneyFlow.SecurityServices.AuthServiceImpl;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
@Configuration
public class AuthTokenFilter extends OncePerRequestFilter {

	private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);
	
	@Autowired
	protected JwtUtils jwtUtils;
	
	@Autowired
	protected AuthServiceImpl authService;
	

	public AuthTokenFilter(JwtUtils jwtUtils,AuthServiceImpl authService)
	{
		this.jwtUtils = jwtUtils;
		this.authService = authService;
	}
	
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		if(request.getHeader("Authorization")!=null && !request.getHeader("Authorization").equalsIgnoreCase("Bearer "))
		{
        System.out.println(response.getHeader("Authorization"));
		String jwt = setAuthentication(request);
		if(jwt!=null) {
			response.setHeader("Access-Control-Allow-Origin",request.getHeader("Origin"));
			response.setHeader("Access-Control-Allow-Credentials", "true");
			response.setHeader("Access-Control-Allow-Methods", "OPTIONS,POST,GET,PUT,DELETE");
			response.setHeader("Access-Control-Max-Age", "3600");
			response.setHeader("Access-Control-Allow-Headers", "Authorization,auth-user,Content-Type,Accept, X-Requested-With,remember-me");
		}else {
			response.setHeader("Access-Control-Allow-Origin",request.getHeader("Origin"));
			response.setHeader("Access-Control-Allow-Credentials", "true");
			response.setHeader("Access-Control-Allow-Methods", "OPTIONS,POST");
			response.setHeader("Access-Control-Max-Age", "3600");
			response.setHeader("Access-Control-Allow-Headers", "Authorization,Content-Type,Accept, X-Requested-With,remember-me");
			}
		}else {
				response.setHeader("Access-Control-Allow-Origin",request.getHeader("Origin"));
				response.setHeader("Access-Control-Allow-Credentials", "true");
				response.setHeader("Access-Control-Allow-Methods", "OPTIONS,POST,GET,PUT,DELETE");
				response.setHeader("Access-Control-Max-Age", "3600");
				response.setHeader("Access-Control-Allow-Headers", "Authorization,Content-Type,Accept, X-Requested-With,remember-me");
		}
		
		
		filterChain.doFilter(request, response);
		
	}
	
	@Bean
	protected FilterRegistrationBean<AuthTokenFilter> makeFilter(){
		
		FilterRegistrationBean <AuthTokenFilter> bean = new FilterRegistrationBean<>();
		bean.setFilter(new AuthTokenFilter(this.jwtUtils,this.authService));
		bean.addUrlPatterns("/api/data/**");
		return bean;
	}
	
	protected String parseJwt(HttpServletRequest request)
	{
		String authHeader = request.getHeader("Authorization");
		
		if(StringUtils.hasText(authHeader)&&authHeader.startsWith("Bearer "))
		{
			return authHeader.split(" ")[1].trim();
		}
		return null;
	}

	protected String setAuthentication (HttpServletRequest request)
	{
		try {
			String jwt = parseJwt(request);
			String username = this.jwtUtils.getUsernameFromJwtToken(jwt);
			if((jwt != null)&&(username != null))
			{
				
				UserDetails userDetails = authService.loadUserByUsername(username);
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				SecurityContextHolder.getContext().setAuthentication(authentication);
				return jwt;
			}
		}catch(Exception e) {
			final String error = "Unable to set authentication "+e;
			logger.error(error);
		}
		return null;
	}

}
