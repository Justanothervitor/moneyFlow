package com.justanothervitor.api_2.controllers;

import com.justanothervitor.api_2.config.auth.JwtUtil;
import com.justanothervitor.api_2.exceptions.NotFoundException;
import com.justanothervitor.api_2.models.Enums.VerificationType;
import com.justanothervitor.api_2.models.User;
import com.justanothervitor.api_2.models.payloads.request.EmailRequest;
import com.justanothervitor.api_2.models.payloads.request.SendVerificationCodeRequest;
import com.justanothervitor.api_2.models.payloads.request.VerifyCodeRequest;
import com.justanothervitor.api_2.models.payloads.request.VerifyTwoFactorCodeRequest;
import com.justanothervitor.api_2.models.payloads.response.SuccessfulAuthResponse;
import com.justanothervitor.api_2.models.payloads.response.VerificationResponse;
import com.justanothervitor.api_2.repositories.UserRepositories;
import com.justanothervitor.api_2.services.VerificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/security/code/2fa/")
public class TwoFactorController {

    private final UserRepositories userRepositories;
    private final VerificationService verificationService;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("enable")
    public ResponseEntity<?> enable2FA(Authentication authentication) throws NotFoundException {
        try{
            String username = authentication.getName();
            User user = userRepositories.findByUsername(username).or(()->userRepositories.findByEmail(username)).orElseThrow(()-> new NotFoundException("Usuário não encontrado!"));
            user.setTwofactor(true);
            userRepositories.save(user);
            return ResponseEntity.ok(Map.of("success", true,"message","2FA ativado com sucesso!"));
        }catch(Exception e){
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("disable")
    public ResponseEntity<?> disable2FA(@RequestParam String password, Authentication authentication) throws NotFoundException {
        try{
            String username = authentication.getName();
            User user = userRepositories.findByUsername(username).or(()->userRepositories.findByEmail(username)).orElseThrow(()-> new NotFoundException("Usuário não encontrado!"));
            if(!passwordEncoder.matches(password, user.getPassword())){
                return ResponseEntity.badRequest().body(Map.of("error", "Senha incorreta!"));
            }
            user.setTwofactor(false);
            userRepositories.save(user);
            return ResponseEntity.ok(Map.of("success", true,"message","2FA desativado com sucesso!"));
        }catch(Exception e){
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("send-code")
    public ResponseEntity<?> sendTwoFactorCode(@Valid @RequestBody SendVerificationCodeRequest request){
        try{
            User user = userRepositories.findByEmail(request.getEmail()).orElseThrow(()-> new NotFoundException("Usuário não encontrado!"));
            if(!user.isTwofactor()){
                return ResponseEntity.badRequest().body(Map.of("error","2FA não está ativado para esta conta"));
            }
            VerificationResponse response = verificationService.generateAndSendCode(user,request.getType());
            return ResponseEntity.ok(response);
        }catch(Exception e){
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("verify")
    public ResponseEntity<?> verifyTwoFactorCode(@Valid @RequestBody VerifyTwoFactorCodeRequest request){

        try{
            User user = userRepositories.findByEmail(request.getEmail()).orElseThrow(()-> new NotFoundException("Usuário não encontrado!"));
            boolean verified = verificationService.verifyCode(user,request.getCode(), VerificationType.TWO_FACTOR_VERIFICATION);

            if(!verified){
                return ResponseEntity.badRequest().body(Map.of("error", "Código incorreto!"));
            }
            String token = jwtUtil.generateToken(user.getUsername());
            return ResponseEntity.ok(new SuccessfulAuthResponse(token,user.getUsername(),user.getEmail()));
        }catch(Exception e){
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }

    }

}
