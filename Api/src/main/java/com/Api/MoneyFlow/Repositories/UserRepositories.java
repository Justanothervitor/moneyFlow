package com.Api.MoneyFlow.Repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.Api.MoneyFlow.Templates.UserTemplate;
import com.Api.MoneyFlow.Domains.UserDomain;

@Repository
public class UserRepositories implements UserTemplate{

	@Autowired
	protected MongoTemplate template;
	
	protected Query usernameQuery(String username) {
		return new Query(Criteria.where("username").is(username));
	}
	
	protected Query emailQuery(String email)
	{
		return new Query(Criteria.where("email").is(email));
	}
	
	protected Query idQuery(String id)
	{
		return new Query(Criteria.where("_id").is(id));
	}
	
	@Override
	public UserDomain findByUsername(String username) {
		Query query =  usernameQuery(username);		
		return this.template.findOne(query, UserDomain.class);
	}

	@Override
	public Boolean usernameExists(String username) {
		Query query = usernameQuery(username);
        return template.findOne(query, UserDomain.class) != null;
	}

	@Override
	public Boolean emailExists(String email) {
		Query query = emailQuery(email);
        return template.findOne(query, UserDomain.class) != null;
	}

	@Override
	public void deleteUser(String username) {
		Query query = usernameQuery(username);
		this.template.findAndRemove(query,UserDomain.class);
		
	}

	@Override
	public void saveUser(UserDomain user) {
		this.template.save(user);
	}

	@Override
	public UserDomain getUserById(String id) {
		Query query = idQuery(id);
		return this.template.findOne(query, UserDomain.class);
	}

}
