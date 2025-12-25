package com.justanothervitor.api_2.config.auth.handlers;

import com.justanothervitor.api_2.config.auth.CustomOAuth2User;
import com.justanothervitor.api_2.config.auth.JwtUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@Component
public class OAuth2AuthenticationSucessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    protected JwtUtil jwtUtil;

    @Value("${app.oauth2.authorized-redirect-uri:http://localhost:3000/oauth2/redirect}")
    private String redirectUri;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException{

        if(response.isCommitted()){
            logger.debug("Response já foi commitada");
            return;
        }

        String targetUrl = determineTargetUrl(request,response,authentication);
        clearAuthenticationAttributes(request);
        getRedirectStrategy().sendRedirect(request,response,targetUrl);
    }

    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication){

        CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
        String token = this.jwtUtil.generateToken(oAuth2User.getEmail());
        return UriComponentsBuilder.fromUriString(redirectUri).queryParam("token",token).build().toUriString();

    }

}
