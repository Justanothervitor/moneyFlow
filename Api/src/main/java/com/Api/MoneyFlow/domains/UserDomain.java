package com.Api.MoneyFlow.domains;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;

@Document(collection="users")
public class UserDomain implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	private String id;
	
	private String username;
	private String email;
	private String password;
	
	@JsonProperty("Annotations")
	@DocumentReference
	@JsonBackReference
	private List<AnnotationDomain> Annotations = new ArrayList<>();
	
	@DocumentReference
	private Set<RolesDomain> Authorization = new HashSet<>();
	
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
	
	public UserDomain(String username,String email,String password, List<AnnotationDomain> annotations)
	{
		this.username = username;
		this.email = email;
		this.password = password;
		this.Annotations = annotations;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<AnnotationDomain> getAnnotations() {
		return Annotations;
	}

	public void setAnnotations(List<AnnotationDomain> annotations) {
		Annotations = annotations;
	}

	public Set<RolesDomain> getAuthorization() {
		return Authorization;
	}

	public void setAuthorization(Set<RolesDomain> authorization) {
		Authorization = authorization;
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
		UserDomain other = (UserDomain) obj;
		return Objects.equals(id, other.id);
	}
	
	
	
}
