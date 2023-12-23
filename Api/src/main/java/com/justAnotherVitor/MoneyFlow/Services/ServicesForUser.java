package com.justAnotherVitor.MoneyFlow.Services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.justAnotherVitor.MoneyFlow.Repository.UserRepository;
import com.justAnotherVitor.MoneyFlow.domain.UserEntity;


@Repository
public class ServicesForUser implements UserRepository{
	
	@Autowired
	private MongoTemplate template;
	
	@Override
	public UserEntity FindByUsername(String username) {
		Query query = new Query();
		query.addCriteria(Criteria.where("username").is(username));
		return this.template.findOne(query, UserEntity.class);
	}

	@Override
	public void DeleteUser(String username) {
		Query query = new Query();
		query.addCriteria(Criteria.where("username").is(username));
		template.findAllAndRemove(query,UserEntity.class);
	}

	@Override
	public UserEntity SaveUser(UserEntity user) {
		return this.template.save(user);
	}

	@Override
	public Boolean UsernameExists(String username) {
		
		Query query = new Query();
		query.addCriteria(Criteria.where("username").is(username));
		if(this.template.findOne(query,UserEntity.class) != null)
		{
			return true;
		}else {
			return false;
		}
	}

	@Override
	public Boolean EmailExists(String email) {
		Query query = new Query();
		query.addCriteria(Criteria.where("email").is(email));
		if(this.template.findOne(query,UserEntity.class) != null)
		{
			return true;
		}else {
			return false;
		}
	}
		
		}
	

