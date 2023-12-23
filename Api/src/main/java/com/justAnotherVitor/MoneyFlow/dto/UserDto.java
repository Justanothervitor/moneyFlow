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
	private String username;
	private String email;
	
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
		username = obj.getUsername();
		email = obj.getEmail();
		notes = obj.getNotes();
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
