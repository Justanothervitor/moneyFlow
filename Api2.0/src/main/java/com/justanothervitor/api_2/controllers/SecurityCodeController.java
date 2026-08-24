package com.justanothervitor.api_2.controllers;

import com.justanothervitor.api_2.models.Enums.VerificationType;
import com.justanothervitor.api_2.models.User;
import com.justanothervitor.api_2.models.VerificationCode;
import com.justanothervitor.api_2.models.payloads.request.SendVerificationCodeRequest;
import com.justanothervitor.api_2.models.payloads.request.VerifyCodeRequest;
import com.justanothervitor.api_2.models.payloads.response.VerificationResponse;
import com.justanothervitor.api_2.repositories.UserRepositories;
import com.justanothervitor.api_2.services.CodeGeneratorService;
import com.justanothervitor.api_2.services.UserServices;
import com.justanothervitor.api_2.services.VerificationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/security/service/")
public class SecurityCodeController {

    @Autowired
    private VerificationService verificationService;

    @Autowired
    private UserRepositories userRepositories;

    @PostMapping("code/send")
    public ResponseEntity<?> sendVerificationCode(@Valid @RequestBody SendVerificationCodeRequest request) {
        try{
            User user = userRepositories.findByEmail(request.getEmail()).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
            VerificationResponse response = verificationService.generateAndSendCode(user,request.getType());
            return ResponseEntity.ok(response);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(Map.of("error",e.getMessage()));
        }
    }

    @PostMapping("code/verify")
    public ResponseEntity<?> verifyCode(@Valid @RequestBody VerifyCodeRequest request, Authentication authentication) {
        try{
            String username = authentication.getName();
            User user = userRepositories.findByUsername(username).or(() -> userRepositories.findByEmail(username))
                    .orElseThrow(()-> new RuntimeException("Usuário não encontrado"));
            boolean verified = verificationService.verifyCode(user, request.getCode(),request.getType());

            return ResponseEntity.ok(Map.of("verified", verified,"Message","Código enviado com sucesso"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error",e.getMessage()));
        }

    }

    @PostMapping("code/resend")
    public ResponseEntity<?> resendCode(@Valid @RequestBody SendVerificationCodeRequest request){
        try{
            User user = userRepositories.findByEmail(request.getEmail()).orElseThrow(()-> new RuntimeException("Usuário não encontrado"));
            VerificationResponse response = verificationService.generateAndSendCode(user,request.getType());
            return ResponseEntity.ok(response);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(Map.of("error",e.getMessage()));
        }
    }

}
