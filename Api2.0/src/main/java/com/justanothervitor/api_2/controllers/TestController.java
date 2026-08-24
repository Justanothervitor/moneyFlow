package com.justanothervitor.api_2.controllers;

import com.justanothervitor.api_2.models.payloads.request.EmailTestRequest;
import com.justanothervitor.api_2.services.EmailService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/api/v2/test")
public class TestController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/sendemail")
    public ResponseEntity<?> testEmail(@Valid @RequestBody EmailTestRequest emailTestRequest){
        emailService.sendHtmlEmail(emailTestRequest.getEmail(), "Test","test-email",new HashMap<>());
        return ResponseEntity.ok().build();
    }
}
