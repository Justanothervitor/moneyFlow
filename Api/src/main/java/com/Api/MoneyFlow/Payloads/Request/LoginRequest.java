package com.Api.MoneyFlow.Payloads.Request;

import com.Api.MoneyFlow.MainCfg.Serializers.CustomJsonLoginSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@JsonSerialize(using=CustomJsonLoginSerializer.class)
@Getter
@Setter
public class LoginRequest {

	@NotBlank
	private String username;
	
	@NotBlank
	private String password;

	public LoginRequest(String username, String password) {
		this.username = username;
		this.password = password;
	}
}
