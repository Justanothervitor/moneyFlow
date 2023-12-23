package com.justAnotherVitor.MoneyFlow.config.security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.justAnotherVitor.MoneyFlow.Repository.UserRepository;
import com.justAnotherVitor.MoneyFlow.domain.UserEntity;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
	
	@Autowired
	UserRepository userRepo;
	
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
			UserEntity user = this.userRepo.FindByUsername(username);
			if(user == null)
			{
				throw(new UsernameNotFoundException("User not found with username:"+username));
			}
		return UserDetailsImpl.build(user);
	}

}