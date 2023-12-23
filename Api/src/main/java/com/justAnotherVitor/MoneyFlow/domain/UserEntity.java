package com.justAnotherVitor.MoneyFlow.domain;
//Essa classe é uma das classes que tem definição de documento que no caso é a classe de usuários
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
//import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.justAnotherVitor.MoneyFlow.domain.roles.RolesEntity;

@Document(collection = "users")
public class UserEntity implements Serializable{

	@Id
	private String id;
	
	private static final long serialVersionUID = 1L;
		
	private String username;
	private String email;
	private String password;
	
	@DocumentReference
	private Set<RolesEntity> roles;
	
	/*Relação um com muitos entre Entidade de usuário e Entidade de notas e ao mesmo tempo tem uma annotation
	mostrando o seu nome no documento JSON*/
		@JsonProperty("Notes")
		@DocumentReference
		@JsonBackReference
		private List <NoteEntity> notes = new ArrayList<>();
	
	public UserEntity() 
	{
		
	}

	public UserEntity(String username, String email, String password)
	{
		super();
		this.username = username;
		this.email = email;
		this.password = password;
	}
	
	public UserEntity(String id,String username, String email, String password, Set<RolesEntity>roles) {
		super();
		this.id = id;
		this.email = email;
		this.username = username;
		this.password = password;
		this.roles = roles;
	}

	public String getId()
	{
		return id;
	}
	
	public void setId(String id)
	{
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
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	
	
	public Set<RolesEntity> getRoles() {
		return roles;
	}

	public void setRoles(Set<RolesEntity> roles) {
		this.roles = roles;
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
