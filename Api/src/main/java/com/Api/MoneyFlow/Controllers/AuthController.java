package com.Api.MoneyFlow.Controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Api.MoneyFlow.MainCfg.JwtCfg.JwtUtils;
import com.Api.MoneyFlow.Repositories.RolesRepository;
import com.Api.MoneyFlow.Repositories.UserRepositories;
import com.Api.MoneyFlow.SecurityServices.UserDetailsImpl;
import com.Api.MoneyFlow.Domains.ERole;
import com.Api.MoneyFlow.Domains.RolesDomain;
import com.Api.MoneyFlow.Domains.UserDomain;
import com.Api.MoneyFlow.Payloads.Request.LoginRequest;
import com.Api.MoneyFlow.Payloads.Request.SignupRequest;
import com.Api.MoneyFlow.Payloads.Response.MessageResponse;
import com.Api.MoneyFlow.Payloads.Response.UserInfoResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/auth")
@CrossOrigin(origins={"http://localhost:46649","https://piranha-wanted-shark.ngrok-free.app"},allowCredentials = "true")
public class AuthController {

	@Autowired
	protected AuthenticationManager authenticationManager;
	
	@Autowired
	protected UserRepositories userRepositories;
	
	@Autowired
	protected RolesRepository rolesRepo;
	
	@Autowired
	protected JwtUtils jwtUtils;
	
	@Autowired
	protected PasswordEncoder passwordEncoder;

	
	@PostMapping("/login")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest login)
	{
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(login.getUsername(),login.getPassword()));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> role = userDetails.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.collect(Collectors.toList());
		
		
		return ResponseEntity.ok().body(new UserInfoResponse(jwt,"Bearer",userDetails.getId(),userDetails.getUsername(),
				userDetails.getEmail(),role));
	}
	
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signup)
	{
		if(userRepositories.usernameExists(signup.getUsername()))
		{
			return ResponseEntity.badRequest().body(new MessageResponse("Error:Username already taken!"));
		}
		if(userRepositories.emailExists(signup.getEmail()))
		{
			return ResponseEntity.badRequest().body(new MessageResponse("Error:Email already taken!"));
		}
		
		UserDomain user = new UserDomain(signup.getUsername(),signup.getEmail(),
				passwordEncoder.encode(signup.getPassword()));

		Set<RolesDomain> role = new HashSet<>();
			RolesDomain userRole = rolesRepo.findRoleByName(ERole.NORMAL);
			role.add(userRole);
		user.setAuthorization(role);
		userRepositories.saveUser(user);
		
		return ResponseEntity.ok().body(new MessageResponse("User registered successfully!"));
	}
	
}
