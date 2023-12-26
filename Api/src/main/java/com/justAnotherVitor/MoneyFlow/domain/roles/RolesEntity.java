package com.justAnotherVitor.MoneyFlow.domain.roles;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "roles")
public class RolesEntity {
	
	@Id
	private String id;
	
	@Indexed(unique = true,direction = IndexDirection.DESCENDING)
	private String role;
	
	private ERole name;

	public RolesEntity(String role,ERole name) {
	
		this.role = role;
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public ERole getName() {
		return name;
	}

	public void setName(ERole name) {
		this.name = name;
	}
	
		
}
