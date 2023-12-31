package com.justAnotherVitor.MoneyFlow.domain;
//Essa classe é uma das classes que tem definição de documento que no caso é a classe de usuários
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
//import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;

@Document(collection = "users")
public class UserEntity implements Serializable{

	@Id
	private String id;
	
	private static final long serialVersionUID = 1L;
		
	private String name;
	private String login;
	private String password;
/*Relação um com muitos entre Entidade de usuário e Entidade de notas e ao mesmo tempo tem uma annotation
mostrando o seu nome no documento JSON*/
	@JsonProperty("Notes")
	@DocumentReference
	@JsonBackReference
	private List <NoteEntity> notes = new ArrayList<>();
	
	public UserEntity() 
	{
		
	}

	public UserEntity(String id,String name, String login, String password) {
		super();
		this.id = id;
		this.name = name;
		this.login = login;
		this.password = password;
	}

	public String getId()
	{
		return id;
	}
	
	public void setId(String id)
	{
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setNotes(List<NoteEntity> notes) {
		this.notes = notes;
	}
	public List<NoteEntity> getNotes() {
		return notes;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserEntity other = (UserEntity) obj;
		return Objects.equals(id, other.id);
	}


	
	
	
}
