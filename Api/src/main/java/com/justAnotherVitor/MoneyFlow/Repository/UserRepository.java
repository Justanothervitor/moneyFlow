package com.justAnotherVitor.MoneyFlow.Repository;
import org.springframework.stereotype.Repository;

import com.justAnotherVitor.MoneyFlow.domain.UserEntity;

@Repository
public interface UserRepository{
	
	UserEntity FindByUsername(String username);
	Boolean UsernameExists(String username);
	Boolean EmailExists(String email);
	void DeleteUser(String username);
	UserEntity SaveUser(UserEntity user);
	
}
