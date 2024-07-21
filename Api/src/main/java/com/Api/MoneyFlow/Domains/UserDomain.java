package com.Api.MoneyFlow.Domains;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Document(collection="users")
public class UserDomain implements Serializable {
	
	@Serial
	private static final long serialVersionUID = 1L;

	@Id
	protected String id;
	
	protected String username;
	protected String email;
	protected String password;
	
	@JsonProperty("UserAnnotations")
	@DocumentReference
	@JsonBackReference
	protected List<AnnotationDomain> UserAnnotations = new ArrayList<>();
	
	@DocumentReference
	protected Set<RolesDomain> Authorization = new HashSet<>();
	
	public UserDomain ()
	{
		
	}
	
	public UserDomain(String username,String email,String password) {
		this.username = username;
		this.email = email;
		this.password = password;
	}
	
	public UserDomain(String username)
	{
		this.username = username;
	}
	
	public UserDomain(String username,String password)
	{
		this.username = username;
		this.password = password;
	}
	
	public UserDomain(String username,String email,String password, List<AnnotationDomain> UserAnnotations)
	{
		this.username = username;
		this.email = email;
		this.password = password;
		this.UserAnnotations = UserAnnotations;
	}

}
