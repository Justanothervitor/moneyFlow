package com.justanothervitor.api_2.controllers;
import com.justanothervitor.api_2.exceptions.NotFoundException;
import com.justanothervitor.api_2.models.Enums.AuthProvider;
import com.justanothervitor.api_2.models.Enums.VerificationType;
import com.justanothervitor.api_2.models.User;
import com.justanothervitor.api_2.models.VerificationCode;
import com.justanothervitor.api_2.models.payloads.request.CreateUserPayload;
import com.justanothervitor.api_2.models.payloads.request.LoginPayload;
import com.justanothervitor.api_2.models.payloads.request.SendVerificationCodeRequest;
import com.justanothervitor.api_2.models.payloads.request.VerifyCodeRequest;
import com.justanothervitor.api_2.models.payloads.response.SuccessfulAuthResponse;
import com.justanothervitor.api_2.repositories.UserRepositories;
import com.justanothervitor.api_2.services.AuthService;
import com.justanothervitor.api_2.services.VerificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/security/auth")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class AuthController {

    private final AuthService authService;
    private final VerificationService verificationService;
    private final UserRepositories userRepositories;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginPayload request){
        try{
            SuccessfulAuthResponse response = authService.login(request);

            User user = userRepositories.findByUsername(request.getUsername())
                    .or(()-> userRepositories.findByEmail(request.getUsername())).orElseThrow(()-> new RuntimeException("Usuário não encontrado"));
            if(!user.isEmailVerified() && user.getProvider() == AuthProvider.LOCAL){
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("error","Email não verificado",
                        "Message","Por favor, verifique seu email antes de fazer login","email",user.getEmail()));
            }
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error","Credenciais inválidas"));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody CreateUserPayload request)
    {
       try{
           User user = authService.register(request);
           verificationService.generateAndSendCode(user, VerificationType.EMAIL_VERIFICATION);
           return ResponseEntity.ok(Map.of("message","Usuário registrado com sucesso!","username",user.getUsername(),"emailSent",
                   true,"info","Um código de verificação foi enviado para o seu email"));
       } catch (Exception e) {
           return ResponseEntity.badRequest().body(Map.of("error",e.getMessage()));
       }
    }

    @PostMapping("/verify-email")
    public ResponseEntity<?> verifyEmail(@Valid @RequestBody VerifyCodeRequest request){
        try{
            VerificationCode verificationCode = verificationService.findByCode(request.getCode(),  VerificationType.EMAIL_VERIFICATION);

            User user = verificationCode.getUser();

            boolean verified = verificationService.verifyCode(user, request.getCode(), VerificationType.EMAIL_VERIFICATION);

            return ResponseEntity.ok(Map.of("verified",verified,"message","Email verificado com sucesso! Você já pode fazer login."));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(Map.of("error",e.getMessage()));
        }
    }

    @PostMapping("/send-code")
    public ResponseEntity<?> sendCode(@Valid @RequestBody SendVerificationCodeRequest request) throws NotFoundException {
        try{
            User user = userRepositories.findByEmail(request.getEmail()).orElseThrow(()-> new NotFoundException("Não foi possível encontrar um usuário com esse email."));
            verificationService.generateAndSendCode(user, request.getType());
            return ResponseEntity.ok(Map.of("message","Code sent, verify your email for the code"));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(Map.of("error",e.getMessage()));
        }
    }



}
