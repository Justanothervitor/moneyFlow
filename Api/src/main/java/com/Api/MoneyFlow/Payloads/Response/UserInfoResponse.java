package com.Api.MoneyFlow.Payloads.Response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserInfoResponse {

	protected String token;
	protected String type = "Bearer";
	protected String id;
	protected String username;
	protected String email;
	protected List<String> role;
	
	public UserInfoResponse(String token, String type, String id, String username, String email,List<String> role)
	{
		this.token = token;
		this.type = type;
		this.id = id;
		this.username = username;
		this.email = email;
		this.role = role;
	}

}
