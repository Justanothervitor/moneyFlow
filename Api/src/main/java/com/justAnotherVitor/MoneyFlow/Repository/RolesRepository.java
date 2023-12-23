package com.justAnotherVitor.MoneyFlow.Repository;

import com.justAnotherVitor.MoneyFlow.domain.roles.ERole;
import com.justAnotherVitor.MoneyFlow.domain.roles.RolesEntity;

public interface RolesRepository {
	
	RolesEntity findRoleByName(ERole role);
	RolesEntity saveRole(RolesEntity role);

}
