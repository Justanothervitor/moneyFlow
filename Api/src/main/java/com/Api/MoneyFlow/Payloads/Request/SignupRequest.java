package com.Api.MoneyFlow.Payloads.Request;

import java.util.Set;

import com.Api.MoneyFlow.MainCfg.Serializers.CustomJsonRegisterSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@JsonSerialize(using=CustomJsonRegisterSerializer.class)
@Getter
@Setter
public class SignupRequest {
	
	@NotBlank
	@Size(min=3,max=10)
	private String username;
	
	@NotBlank
	@Email
	@Size(max=50)
	private String email;
	
	private Set<String> roles;
	
	@NotBlank
	@Size(min=6,max=40)
	private String password;

	public SignupRequest(String username, String email, Set<String> roles, String password) {
		this.username = username;
		this.email = email;
		this.roles = roles;
		this.password = password;
	}
}
