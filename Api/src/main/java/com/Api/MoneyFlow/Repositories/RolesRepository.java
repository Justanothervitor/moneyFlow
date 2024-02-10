package com.Api.MoneyFlow.Repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.Api.MoneyFlow.Templates.RolesTemplate;
import com.Api.MoneyFlow.domains.ERole;
import com.Api.MoneyFlow.domains.RolesDomain;

@Repository
public class RolesRepository implements RolesTemplate{

	@Autowired
	private MongoTemplate template;
	
	private Query roleQuery (ERole role)
	{
		Query query = new Query(Criteria.where("role").is(role));
		return query;
	}
	
	@Override
	public RolesDomain findRoleByName(ERole role) {
		Query query = roleQuery(role);
		return template.findOne(query, RolesDomain.class);
	}

	@Override
	public RolesDomain saveRole(RolesDomain obj) {
		return this.template.save(obj);
	}

}
