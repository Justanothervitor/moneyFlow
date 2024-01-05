package com.justAnotherVitor.MoneyFlow.Controller;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.justAnotherVitor.MoneyFlow.Services.RolesServices;
import com.justAnotherVitor.MoneyFlow.Services.ServicesForUser;
import com.justAnotherVitor.MoneyFlow.config.jwt.JwtUtils;
import com.justAnotherVitor.MoneyFlow.config.security.services.UserDetailsImpl;
import com.justAnotherVitor.MoneyFlow.domain.UserEntity;
import com.justAnotherVitor.MoneyFlow.domain.roles.ERole;
import com.justAnotherVitor.MoneyFlow.domain.roles.RolesEntity;
import com.justAnotherVitor.MoneyFlow.payload.request.LoginRequest;
import com.justAnotherVitor.MoneyFlow.payload.request.SignupRequest;
import com.justAnotherVitor.MoneyFlow.payload.response.JwtResponse;
import com.justAnotherVitor.MoneyFlow.payload.response.MessageResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value ="api/auth")
@CrossOrigin(origins="localhost:3000")
public class AuthController {


		@Autowired
		AuthenticationManager authenticationManager;
		
		@Autowired
		ServicesForUser userService;
		
		@Autowired
		RolesServices roleService;
		
		@Autowired
		PasswordEncoder encoder;
		
		@Autowired
		JwtUtils jwtUtils;
		
		@PostMapping(value="/login")
		public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest)
		{
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),loginRequest.getPassword()));
			
			SecurityContextHolder.getContext().setAuthentication(authentication);
			
			String jwt = jwtUtils.generateJwtToken(authentication);
			
			UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
			List<String> roles = userDetails.getAuthorities().stream()
					.map(item-> item.getAuthority())
					.collect(Collectors.toList());
			
			return ResponseEntity.ok(new JwtResponse(jwt,
					userDetails.getId(),
					"Bearer",
					userDetails.getUsername(),
					userDetails.getEmail(),
					roles));
		}
		
		@PostMapping(value="/signup")
		public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest)
		{
			if(userService.UsernameExists(signupRequest.getUsername())) {
				return ResponseEntity
						.badRequest()
						.body(new MessageResponse("Error:Username is already taken!"));
			}
			if(userService.EmailExists(signupRequest.getEmail()))
			{
				return ResponseEntity
						.badRequest()
						.body(new MessageResponse("Error:Email is already taken!"));
			}
			
			UserEntity user = new UserEntity(signupRequest.getUsername(),
					signupRequest.getEmail(),
					encoder.encode(signupRequest.getPassword()));
			
			Set<String> strRoles = signupRequest.getRoles();
			Set<RolesEntity> roles = new HashSet<>();
			
			if(strRoles == null)
			{
				RolesEntity userRole = roleService.findRoleByName(ERole.NORMAL);
				roles.add(userRole);
			}else {
				strRoles.forEach(role ->{
					switch(role) {
					
					case "ADMIN":
						RolesEntity adminRole = roleService.findRoleByName(ERole.ADMIN);
						roles.add(adminRole);
						break;
						
					case "ENTERPRESS":
						RolesEntity EnterpressRole = roleService.findRoleByName(ERole.ENTERPRESS);
						roles.add(EnterpressRole);
						break;
						
					default:
						RolesEntity userRole = roleService.findRoleByName(ERole.NORMAL);
						roles.add(userRole);
						break;
					}
				});
			}
			user.setRoles(roles);
			userService.SaveUser(user);
			
			return ResponseEntity.ok(new MessageResponse("User registered sucessfully"));
		}
		
	}
	
	
