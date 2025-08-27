package com.Api.MoneyFlow.SecurityServices;

import java.io.Serial;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.Api.MoneyFlow.Domains.RolesDomain;
import com.Api.MoneyFlow.Domains.UserDomain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import static io.jsonwebtoken.lang.Collections.isEmpty;

public class UserDetailsImpl implements UserDetails{
	
	@Serial
	private static final long serialVersionUID = 1L;
	@Getter
    private final String id;
    private final String username;
	@Getter
    private final String email;
	
	@JsonIgnore
	private final String password;
	
	private final Collection<? extends GrantedAuthority> authority;
	private final UserDomain userOrigin;

	public UserDetailsImpl(final String id,final String username,final String email,final String password,final Collection<? extends GrantedAuthority> authority,final UserDomain userOrigin)
	{
		super();
		this.id = id;
		this.username = username;
		this.email = email;
		this.password = password;
		this.authority = authority;
		this.userOrigin = userOrigin;
	}
	
	public UserDetailsImpl(final UserDomain user)
	{
		this.id = user.getId();
		this.username = user.getUsername();
		this.email = user.getEmail();
		this.password = user.getPassword();
		this.authority = mapToGrandAuthorities(user.getAuthorization());
		this.userOrigin = user;
	}
	
	public UserDetailsImpl(final UserBuilder userBuilder)
	{
		this.id = userBuilder.id;
		this.username = userBuilder.username;
		this.email = userBuilder.email;
		this.password = userBuilder.password;
		this.authority = userBuilder.authority;
		this.userOrigin = userBuilder.userOrigin;
		
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		if(isEmpty(this.authority))
		{
			return mapToGrandAuthorities(this.userOrigin.getAuthorization());
		}
		return authority;
	}


    @Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public String getPassword() {
		return password;
	}

	public UserDomain getUser()
	{
		return userOrigin;
	}
	private static List<GrantedAuthority> mapToGrandAuthorities(final Collection<RolesDomain> authorities)
	{
		return authorities.stream()
				.map(authority-> new SimpleGrantedAuthority(authority.getRole().toString()))
				.collect(Collectors.toList());
	}
	
	public static class UserBuilder{
		private String id;
		private String username;
		private String email;
		private String password;
		private Collection<? extends GrantedAuthority> authority;
		private UserDomain userOrigin;
		
		public UserBuilder setId(String id) {
			this.id = id;
			return this;
		}

		public UserBuilder setUsername(String username) {
			this.username = username;
			return this;
		}

		public UserBuilder setEmail(String email) {
			this.email = email;
			return this;
		}

		public UserBuilder setPassword(String password) {
			this.password = password;
			return this;
		}

		public UserBuilder setAuthority(Collection<? extends GrantedAuthority> authority) {
			this.authority = authority;
			return this;
		}

		public UserBuilder setUserOrigin(UserDomain userOrigin) {
			this.userOrigin = userOrigin;
			return this;
		}
		
		public UserDetailsImpl build()
		{
			return new UserDetailsImpl(this);
		}
	}
}

		
