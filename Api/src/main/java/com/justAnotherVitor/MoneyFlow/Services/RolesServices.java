package com.justAnotherVitor.MoneyFlow.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.justAnotherVitor.MoneyFlow.Repository.RolesRepository;
import com.justAnotherVitor.MoneyFlow.domain.roles.ERole;
import com.justAnotherVitor.MoneyFlow.domain.roles.RolesEntity;

@Repository
public class RolesServices implements RolesRepository{

	@Autowired
	private MongoTemplate template;
	
	@Override
	public RolesEntity findRoleByName(ERole role) {
		Query query = new Query();
		query.addCriteria(Criteria.where("name").is(role));
		RolesEntity foundRole = this.template.findOne(query,RolesEntity.class);
		return foundRole;
	}

	@Override
	public RolesEntity saveRole(RolesEntity role) {
		return this.template.save(role);
	}

}
