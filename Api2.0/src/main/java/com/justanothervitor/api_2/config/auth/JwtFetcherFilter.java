package com.justanothervitor.api_2.config.auth;

import ch.qos.logback.core.util.StringUtil;
import com.justanothervitor.api_2.models.TokenHolder;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtFetcherFilter extends OncePerRequestFilter {

    private final TokenHolder tokenHolder;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = extractToken(request);

        if(StringUtils.hasText(token))
        {
            tokenHolder.setToken(token);
        }

        filterChain.doFilter(request,response);

    }

    private String extractToken(HttpServletRequest request)
    {
        String bearer = request.getHeader("Authorization");
        if(StringUtils.hasText(bearer) && bearer.startsWith("Bearer ")){
            return bearer.substring(7);
        }
        return null;
    }

}
