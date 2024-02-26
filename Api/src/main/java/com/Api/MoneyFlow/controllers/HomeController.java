package com.Api.MoneyFlow.controllers;

import java.time.ZonedDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.Api.MoneyFlow.payloads.response.MessageResponse;

@Controller
@RequestMapping("api/home")
@CrossOrigin(origins="http://localhost:4200",maxAge=3600,allowCredentials="true")
public class HomeController {

	@GetMapping("/welcome")
	public ResponseEntity<?> welcomeResponse ()
	{
		return ResponseEntity.ok(new MessageResponse("Welcome to MoneyFlow\n The actual date is"+ZonedDateTime.now()));
	}
	
	@GetMapping("/logged")
	public ResponseEntity<?> welcomeLoggedResponse()
	{
		return ResponseEntity.ok(new MessageResponse("Welcome to MoneyFlow logged user\n The actual date is "+ZonedDateTime.now()));
	}
	
	
}
