package com.justanothervitor.api_2.controllers;

import com.justanothervitor.api_2.config.auth.CustomOAuth2User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v2/oauth2")
public class OAuth2Controller {

    @GetMapping("/userr")
    public ResponseEntity<?> getCurrentUser(@AuthenticationPrincipal CustomOAuth2User oauth2User)
    {
        if(oauth2User == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    return ResponseEntity.ok(Map.of("id",oauth2User.getId(),"email",oauth2User.getEmail(),"name", oauth2User.getName()));
    }
}
