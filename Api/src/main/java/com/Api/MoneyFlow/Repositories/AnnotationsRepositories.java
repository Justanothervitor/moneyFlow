package com.Api.MoneyFlow.Repositories;

import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.Api.MoneyFlow.Templates.AnnotationTemplate;
import com.Api.MoneyFlow.domains.AnnotationDomain;
import com.Api.MoneyFlow.domains.UserDomain;
import com.Api.MoneyFlow.payloads.request.InputAnnotationRequest;

@Repository
public class AnnotationsRepositories implements AnnotationTemplate{

	@Autowired
	private MongoTemplate template;
	
	@Autowired 
	private UserRepositories userRepo;
	
	private String parseAuthentication(String authentication)
	{
		
		if(StringUtils.hasText(authentication)&&authentication.startsWith("Bearer"))
		{
			return authentication.substring(7,authentication.length());
		}
		return null;
	}
	
	private AnnotationDomain updateUserAndAdd(AnnotationDomain obj)
	{
		template.update(UserDomain.class)
		.matching(Criteria.where("_id").is(obj.getUser().getId()))
		.apply(new Update().push("Annotations").value(obj))
		.first();
		
		return obj;
		
	}
	
	private AnnotationDomain updateUserAndRemove(AnnotationDomain obj)
	{
		template.update(UserDomain.class)
		.matching(Criteria.where("_id").is(obj.getUser().getId()))
		.apply(new Update().pull("Annotations",obj))
		.first();
		return obj;
	}
	
	private AnnotationDomain updateUserAndAnnotation(AnnotationDomain obj)
	{
		Query query = new Query(Criteria.where("id").is(obj.getUser().getId())
				.and("Annotations").is(obj.getUser().getAnnotations())
				.and("noteId").is(obj.getNoteId()));
		Update update = new Update().set("name", obj.getName())
				.set("value",obj.getValue()).set("description", obj.getDescription());
		this.template.updateFirst(query, update, UserDomain.class);
		return obj;
	}
	
	private Query idQuery(String id)
	{
		Query query = new Query(Criteria.where("noteId").is(id));
		return query;
	}
	

	@Override
	public List<AnnotationDomain> fecthAnnotationRecent() {
		return this.template.findAll(AnnotationDomain.class);
	}

	@Override
	public AnnotationDomain fetchOneById(String id) {
		Query query = idQuery(id);
		return this.template.findOne(query,AnnotationDomain.class);
	}

	@Override
	public AnnotationDomain createAnnotation(InputAnnotationRequest input) {
		if(input.getInputDate() != null)
		{
			input.setInputDate(ZonedDateTime.now());
		}
		AnnotationDomain obj = new AnnotationDomain(input.getName(),input.getValue(),
				input.getInputDate(),input.getDescription(),userRepo.getActualUser(parseAuthentication(input.getAuth())));
		this.template.save(obj);
		return updateUserAndAdd(obj);
		
	}

	@Override
	public AnnotationDomain updateAnnotation(String id,InputAnnotationRequest input) {
	AnnotationDomain toUpdate = fetchOneById(id);
		toUpdate.setName(input.getName());
		toUpdate.setValue(input.getValue());
		toUpdate.setDescription(input.getDescription());
		return updateUserAndAnnotation(toUpdate);
	}

	@Override
	public void deleteAnnotation(String id) {
		Query query = idQuery(id);
		AnnotationDomain toDelete = template.findOne(query, AnnotationDomain.class);
		this.template.remove(updateUserAndRemove(toDelete));
		
	}
	
}
