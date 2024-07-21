package com.Api.MoneyFlow.Templates;

import com.Api.MoneyFlow.Domains.UserDomain;

public interface UserTemplate {
	
	UserDomain findByUsername(String username);
	UserDomain getUserById(String id);
	Boolean usernameExists(String username);
	Boolean emailExists(String email);
	void deleteUser(String username);
	void saveUser(UserDomain user);
	
}
