package com.Api.MoneyFlow.SecurityServices;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.Api.MoneyFlow.domains.UserDomain;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class UserDetailsImpl implements UserDetails{

	private static final long serialVersionUID = 1L;
	
	private String id;
	private String username,email;
	
	@JsonIgnore
	private String password;
	
	private Collection<? extends GrantedAuthority> authority;

	public UserDetailsImpl(String id,String username,String email,String password, Collection<? extends GrantedAuthority> authority)
	{
		super();
		this.id = id;
		this.username = username;
		this.email = email;
		this.password = password;
		this.authority = authority;
	}
	
	public static UserDetailsImpl build(UserDomain user)
	{
		List<GrantedAuthority> authority = user.getAuthorization().stream()
				.map(role-> new SimpleGrantedAuthority(role.getName()))
				.collect(Collectors.toList());
		
		return new UserDetailsImpl(
				user.getId(),
				user.getUsername(),
				user.getEmail(),
				user.getPassword(),
				authority);
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authority;
	}
	
	public String getId()
	{
		return id;
	}
	
	@Override
	public String getUsername() {
		return username;
	}
	

	public String getEmail()
	{
		return email;
	}
	

	@Override
	public String getPassword() {
		return password;
	}

	
}
