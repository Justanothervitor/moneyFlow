package com.Api.MoneyFlow.Templates;

import com.Api.MoneyFlow.domains.UserDomain;

public interface UserTemplate {
	
	UserDomain findByUsername(String username);
	Boolean usernameExists(String username);
	Boolean emailExists(String email);
	void deleteUser(String username);
	UserDomain saveUser(UserDomain user);
	
}
