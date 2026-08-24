package com.justanothervitor.api_2.config.auth;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RateLimiterFilter extends OncePerRequestFilter {

    private final Map<String, List<LocalDateTime>> requestsCount = new ConcurrentHashMap<>();
    private static final int MAX_REQUESTS = 5;
    private static final int TIME_WINDOW_MINUTES = 15;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String ip = request.getRemoteAddr();
        String path = request.getRequestURI();

        if(path.contains("/send-code") || path.contains("/forgot/password"))
        {
            if(isRateLimitExceeded(ip)){
                response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
                response.getWriter().write("{\"error\":\"Muitas tentativas. Tente novamente mais tarde.\"}");
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    private boolean isRateLimitExceeded(String ip) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime windowStart = now.minusMinutes(TIME_WINDOW_MINUTES);
        requestsCount.putIfAbsent(ip,new ArrayList<>());
        List<LocalDateTime> requests = requestsCount.get(ip);

        if(requests.size() >= MAX_REQUESTS){
          return true;
        }
        requests.add(now);
        return false;
    }
}
