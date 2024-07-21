package com.Api.MoneyFlow.Domains;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter
@Getter
@Document(collection="Roles")
public class RolesDomain {
	
	@Id
	protected String roleId;
	
	protected String name;
	
	protected ERole role;
	

	public RolesDomain(String roleId,String name)
	{
		this.roleId = roleId;
		this.name = name;
	}

	public RolesDomain(String roleId,String name,ERole role)
	{
		this.roleId = roleId;
		this.name = name;
		this.role = role;
	}

	public RolesDomain()
	{

	}
}
