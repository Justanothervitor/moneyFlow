package com.justAnotherVitor.MoneyFlow.Controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
@CrossOrigin(origins="http://localhost:8081",maxAge =3600, allowCredentials = "true")
public class TestController {

		@GetMapping("/all")
		public String allAcess()
		{
			return "Welcome to MoneyFlow.";
		}
		
		@GetMapping("/user")
		@PreAuthorize("hasRole('NORMAL') or hasRole('ENTERPRESS') or hasRole('ADMIN')")
		public String userAccess() {
			return "Welcome to MoneyFlow logged User!";
		}
		
		@GetMapping("/enterpress")
		@PreAuthorize("hasRole('ENTERPRESS') or hasRole('ADMIN')")
		public String enterpressAcess() {
			return "User Content.";
		}
		
		@GetMapping("/admin")
		@PreAuthorize("hasRole('ADMIN')")
		public String adminAccess() {
			return "Admin Board.";
		}
	}
