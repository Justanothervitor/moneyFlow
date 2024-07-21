package com.Api.MoneyFlow.Templates;

import com.Api.MoneyFlow.Domains.ERole;
import com.Api.MoneyFlow.Domains.RolesDomain;

public interface RolesTemplate {
	
	RolesDomain findRoleByName(ERole role);
	RolesDomain saveRole(RolesDomain obj);
	
}
