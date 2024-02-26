package com.Api.MoneyFlow.Repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.Api.MoneyFlow.JwtCfg.JwtUtils;
import com.Api.MoneyFlow.Templates.UserTemplate;
import com.Api.MoneyFlow.domains.UserDomain;

@Repository
public class UserRepositories implements UserTemplate{

	@Autowired
	private MongoTemplate template;
	
	@Autowired
	private JwtUtils jwtUtils;
	
	private Query usernameQuery(String username) {
		Query query = new Query(Criteria.where("username").is(username));
		return query;
	}
	
	private Query emailQuery(String email)
	{
		Query query = new Query(Criteria.where("email").is(email));
		return query;
	}
	
	private Query idQuery(String id)
	{
		Query query = new Query(Criteria.where("_id").is(id));
		return query;
	}
	
	@Override
	public UserDomain findByUsername(String username) {
		Query query =  usernameQuery(username);		
		return this.template.findOne(query, UserDomain.class);
	}
	
	@Override
	public UserDomain getActualUser(String auth)
	{
		String username = this.jwtUtils.getUsernameFromJwtToken(auth);
		return this.findByUsername(username);
	}

	@Override
	public Boolean usernameExists(String username) {
		Query query = usernameQuery(username);
		if(template.findOne(query, UserDomain.class)!= null)
		{
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public Boolean emailExists(String email) {
		Query query = emailQuery(email);
		if(template.findOne(query, UserDomain.class) != null) {
			return true;
		}else {
			return false;
		}
	}

	@Override
	public void deleteUser(String username) {
		Query query = usernameQuery(username);
		this.template.findAndRemove(query,UserDomain.class);
		
	}

	@Override
	public UserDomain saveUser(UserDomain user) {
		return this.template.save(user);
	}

	@Override
	public UserDomain getUserById(String id) {
		Query query = idQuery(id);
		return this.template.findOne(query, UserDomain.class);
	}

}
