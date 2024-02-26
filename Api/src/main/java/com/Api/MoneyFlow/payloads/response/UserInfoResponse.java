package com.Api.MoneyFlow.payloads.response;

import java.util.List;

public class UserInfoResponse {

	private String token;
	private String type = "Bearer";
	private String id;
	private String username;
	private String email;
	private List<AnnotationResponse> annotations;
	private List<String> role;
	
	public UserInfoResponse(String token, String type, String id, String username, String email,List<AnnotationResponse>annotations, List<String> role)
	{
		super();
		this.token = token;
		this.type = type;
		this.id = id;
		this.username = username;
		this.email = email;
		this.annotations = annotations;
		this.role = role;
	}
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public List<AnnotationResponse> getAnnotations() {
		return annotations;
	}

	public void setAnnotations(List<AnnotationResponse> annotations) {
		this.annotations = annotations;
	}

	public List<String> getRole() {
		return role;
	}
	public void setRole(List<String> role) {
		this.role = role;
	}
		
}
