package com.justAnotherVitor.MoneyFlow.dto;
//Classe de transferencia de dados usado para retornar usuários na parte de serviços da entidade de usuário.
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import com.justAnotherVitor.MoneyFlow.Services.Exceptions.ObjectNotFoundException;
import com.justAnotherVitor.MoneyFlow.domain.NoteEntity;
import com.justAnotherVitor.MoneyFlow.domain.UserEntity;

public class UserDto implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private String login;
	
	private List<NoteEntity> notes;
	
	public UserDto(Optional<UserEntity> obj)
	{
		this(obj.orElseThrow(()-> new ObjectNotFoundException("Objeto não encontrado")));
	}
	
	public UserDto()
	{
		
	}
	
	public UserDto(UserEntity obj)
	{
		id = obj.getId();
		name = obj.getName();
		login = obj.getLogin();
		notes = obj.getNotes();
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

	public List<NoteEntity> getNotes() {
		return notes;
	}

	public void setNotes(List<NoteEntity> notes) {
		this.notes = notes;
	}

	/*@Override
	public String toString() {
		return "UserDto [id=" + id + ", name=" + name + ", login=" + login + ", notes=" + notes + "]";
	}
	
*/	
}
