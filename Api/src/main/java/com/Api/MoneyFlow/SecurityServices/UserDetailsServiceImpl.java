package com.Api.MoneyFlow.SecurityServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Api.MoneyFlow.Repositories.UserRepositories;
import com.Api.MoneyFlow.domains.UserDomain;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

	@Autowired
	UserRepositories userRepository;
	
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserDomain user = this.userRepository.findByUsername(username);
		if(user == null)
		{
			throw(new UsernameNotFoundException("User not found with username:"+username));
		}
		return UserDetailsImpl.build(user);
	}

}
