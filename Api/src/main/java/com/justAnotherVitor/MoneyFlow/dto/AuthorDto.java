package com.justAnotherVitor.MoneyFlow.dto;

import java.io.Serializable;
import java.util.Optional;

//import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.justAnotherVitor.MoneyFlow.Services.Exceptions.ObjectNotFoundException;
import com.justAnotherVitor.MoneyFlow.domain.UserEntity;

public class AuthorDto implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String id;
	private String name;

	
	public AuthorDto(Optional<UserEntity> obj)
	{
		this(obj.orElseThrow(()-> new ObjectNotFoundException("Objeto não encontrado")));
	}
	
	public AuthorDto()
	{
		
	}
	
	public AuthorDto(UserEntity obj)
	{
		id = obj.getId();
		name = obj.getName();
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


	/*@Override
	public String toString() {
		return "UserDto [id=" + id + ", name=" + name + ", login=" + login + ", notes=" + notes + "]";
	}
	
*/	
}
