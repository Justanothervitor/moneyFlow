package com.justanothervitor.api_2.controllers;


import com.justanothervitor.api_2.models.payloads.request.CreateUserPayload;
import com.justanothervitor.api_2.models.payloads.request.LoginPayload;
import com.justanothervitor.api_2.services.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v2/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginPayload request){
        try{
            var user = authService.login(request);
            return ResponseEntity.ok(user);
        }catch (Exception e )
        {
            return ResponseEntity.badRequest().body("Não foi possível fazer login, verifique o nome de usuário e senha!");
        }
    }

    @PostMapping
    public ResponseEntity<?> register(@Valid @RequestBody CreateUserPayload request)
    {
        try{
            var user = this.authService.register(request);
            if(!user.isEnabled()){
                return ResponseEntity.badRequest().body("Não foi possível criar uma conta com os dados inseridos!");
            }
        return ResponseEntity.ok("Conta criada com sucesso, por favor realize o login");
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Não foi possível criar uma conta, por favor verifique os campos e tente novamente");
        }
    }

}
