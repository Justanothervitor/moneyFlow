package com.Api.MoneyFlow.Templates;

import com.Api.MoneyFlow.domains.ERole;
import com.Api.MoneyFlow.domains.RolesDomain;

public interface RolesTemplate {
	
	RolesDomain findRoleByName(ERole role);
	RolesDomain saveRole(RolesDomain obj);
	
}
