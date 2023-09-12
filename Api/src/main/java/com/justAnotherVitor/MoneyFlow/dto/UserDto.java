package com.justAnotherVitor.MoneyFlow.dto;

import java.io.Serializable;
import java.util.Optional;

import com.justAnotherVitor.MoneyFlow.domain.UserEntity;

public class UserDto implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private String login;
	
	
	public UserDto(Optional<UserEntity> obj)
	{
		
	}
	
	
	public UserDto()
	{
		
	}
	
	public UserDto(UserEntity obj)
	{
		id = obj.getId();
		name = obj.getName();
		login = obj.getLogin();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	
}
