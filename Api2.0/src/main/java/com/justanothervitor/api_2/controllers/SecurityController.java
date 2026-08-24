package com.justanothervitor.api_2.controllers;

import com.justanothervitor.api_2.models.Enums.AuthProvider;
import com.justanothervitor.api_2.models.Enums.VerificationType;
import com.justanothervitor.api_2.models.User;
import com.justanothervitor.api_2.models.payloads.request.ForgottenPasswordRequest;
import com.justanothervitor.api_2.models.payloads.request.UpdatePasswordRequest;
import com.justanothervitor.api_2.models.payloads.request.VerifyCodeAndUpdatePassword;
import com.justanothervitor.api_2.models.payloads.request.VerifyCodeRequest;
import com.justanothervitor.api_2.models.payloads.response.VerificationResponse;
import com.justanothervitor.api_2.repositories.UserRepositories;
import com.justanothervitor.api_2.services.VerificationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
@RestController
@RequestMapping("/api/v1/security/settings")
public class SecurityController {

    @Autowired
    private UserRepositories userRepositories;

    @Autowired
    private VerificationService verificationService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("password/forgot")
    public ResponseEntity<?> forgotPassword(@Valid @RequestBody ForgottenPasswordRequest request) {
        try{
            User user = userRepositories.findByEmail(request.getEmail()).orElseThrow(()-> new RuntimeException("Email não encontrado"));

            if(user.getProvider() != AuthProvider.LOCAL){
                return ResponseEntity.badRequest().body(Map.of("error","Você se cadrastrou usando"+user.getProvider()+
                        ". Por favor, use esse método para fazer login."));
            }
            VerificationResponse response =  verificationService.generateAndSendCode(user, request.getType());

            return ResponseEntity.ok(Map.of("success",true,"message","Código de recuperação enviado para o seu email",
                    "expiryDate",response.getExpiryDate()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error",e.getMessage()));
        }
    }

    @PostMapping("password/reset")
    public ResponseEntity<?> resetPassword(@Valid @RequestBody VerifyCodeAndUpdatePassword request) {
        try{
            User user = userRepositories.findByEmail(request.getEmail()).orElseThrow(()-> new RuntimeException("Usuário não encontrado."));
            boolean verified = verificationService.verifyCode(user, request.getCode(), VerificationType.PASSWORD_RESET);

            if(!verified){
                return ResponseEntity.badRequest().body(Map.of("error","Código inválido ou expirado"));
            }
            user.setPassword(passwordEncoder.encode(request.getPasswordForChange()));
            userRepositories.save(user);
            return ResponseEntity.ok(Map.of("success",true,"message","Senha alterada com sucesso!"));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(Map.of("error",e.getMessage()));
        }
    }

    @PostMapping("password/change")
    public ResponseEntity<?> changePassword(@Valid @RequestBody UpdatePasswordRequest request, Authentication authentication)
    {
        try{
            String username = authentication.getName();
            User user = userRepositories.findByEmail(username)
                    .or(()-> userRepositories.findByEmail(username)).orElseThrow(()-> new RuntimeException("Usuário não encontrado."));
            if(!passwordEncoder.matches(request.getPassword(),user.getPassword()))
            {
                return ResponseEntity.badRequest().body(Map.of("error","Senha atual incorreta!"));
            }
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            userRepositories.save(user);

            return ResponseEntity.ok(Map.of("success",true,"message","Senha atualizada com sucesso!"));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(Map.of("error",e.getMessage()));
        }
    }

}
