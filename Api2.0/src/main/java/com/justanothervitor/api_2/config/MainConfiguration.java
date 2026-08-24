package com.justanothervitor.api_2.config;


import com.justanothervitor.api_2.config.auth.CustomOAuth2User;
import com.justanothervitor.api_2.config.auth.JwtFetcherFilter;
import com.justanothervitor.api_2.config.auth.JwtFilterChain;
import com.justanothervitor.api_2.config.auth.handlers.OAuth2AuthenticationFailureHandler;
import com.justanothervitor.api_2.config.auth.handlers.OAuth2AuthenticationSucessHandler;
import com.justanothervitor.api_2.config.auth.services.CustomOauth2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.writers.XXssProtectionHeaderWriter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class MainConfiguration {

    protected UserDetailsService userDetailsService;
    protected JwtFilterChain filterChain;
    protected JwtFetcherFilter fetcherFilter;
    protected CustomOauth2Service customOauth2Service;
    protected OAuth2AuthenticationSucessHandler oAuth2AuthenticationSucessHandler;
    protected OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;

    @Autowired
    public MainConfiguration(UserDetailsService userDetailsService, JwtFilterChain filterChain, JwtFetcherFilter fetcherFilter, CustomOauth2Service customOauth2Service, OAuth2AuthenticationSucessHandler oAuth2AuthenticationSucessHandler, OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler) {
        this.userDetailsService = userDetailsService;
        this.filterChain = filterChain;
        this.fetcherFilter = fetcherFilter;
        this.customOauth2Service = customOauth2Service;
        this.oAuth2AuthenticationSucessHandler = oAuth2AuthenticationSucessHandler;
        this.oAuth2AuthenticationFailureHandler = oAuth2AuthenticationFailureHandler;
    }

    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtFetcherFilter jwtFetcherFilter) throws Exception {

        http.
                csrf(AbstractHttpConfigurer::disable)
                .cors(withDefaults()).redirectToHttps(withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v2/auth/**","/api/v2/oauth2/**","/api/v2/test/sendemail").permitAll()
                        .anyRequest().authenticated())
                .oauth2Login(oauth2 -> oauth2.authorizationEndpoint(auth -> auth
                        .baseUri("/api/v2/oauth2/authorize"))
                        .redirectionEndpoint(redirection -> redirection
                                .baseUri("/api/v2/login/oauth2/code/**"))
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(customOauth2Service))
                        .successHandler(this.oAuth2AuthenticationSucessHandler)
                        .failureHandler(this.oAuth2AuthenticationFailureHandler)
                ).addFilterBefore(jwtFetcherFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(filterChain, JwtFilterChain.class);
        return http.build();

    }

    @Bean
    public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource()
    {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:3000"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);
        config.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",config);
        return source;
    }

}
