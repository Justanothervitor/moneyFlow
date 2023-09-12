package com.justAnotherVitor.MoneyFlow.domain;

import java.io.Serializable;
/*import java.util.ArrayList;
import java.util.List;*/
import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public class UserEntity implements Serializable{

	@Id
	private String id;
	
	private static final long serialVersionUID = 1L;
	private String name;
	private String login;
	private String password;
	
	/*@OneToMany(mappedBy="users_notes")
	private List<NoteProperties> userNotes = new ArrayList<>();
	*/
	
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
	
	
	/*public List<NoteProperties> getNotes()
	{
		return userNotes;
	}*/

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
