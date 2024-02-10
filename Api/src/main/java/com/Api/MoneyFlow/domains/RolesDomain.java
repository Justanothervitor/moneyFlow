package com.Api.MoneyFlow.domains;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="Roles")
public class RolesDomain {
	
	@Id
	private String roleId;
	
	private String name;
	
	private ERole role;
	
	
	public RolesDomain(String roleId,String name)
	{
		this.roleId = roleId;
		this.name = name;
	}


	public String getRoleId() {
		return roleId;
	}


	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public ERole getRole() {
		return role;
	}


	public void setRole(ERole role) {
		this.role = role;
	}
	
	
}
