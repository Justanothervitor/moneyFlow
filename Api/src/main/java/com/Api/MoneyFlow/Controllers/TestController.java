package com.Api.MoneyFlow.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class TestController {

    @GetMapping()
    public ResponseEntity<?> greetTheOuterSide()
    {
        return ResponseEntity.ok("Chegou ao outro lado");
    }
}
