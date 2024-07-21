package com.Api.MoneyFlow.Repositories;

import java.util.List;

import com.Api.MoneyFlow.Payloads.Response.AnnotationResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.Api.MoneyFlow.SecurityServices.AuthServiceImpl;
import com.Api.MoneyFlow.Templates.AnnotationTemplate;
import com.Api.MoneyFlow.Domains.AnnotationDomain;
import com.Api.MoneyFlow.Domains.UserDomain;
import com.Api.MoneyFlow.Payloads.Request.InputAnnotationRequest;

@Repository
public class AnnotationsRepositories implements AnnotationTemplate{

	@Autowired
	protected MongoTemplate template;
	
	@Autowired
	protected AuthServiceImpl authService;


	protected AnnotationDomain updateUserAndAdd(@NotNull AnnotationDomain obj)
	{
		template.update(UserDomain.class)
		.matching(Criteria.where("_id").is(obj.getUser().getId()))
		.apply(new Update().push("UserAnnotations").value(obj))
		.first();
		
		return obj;
	}
	
	protected Query fetchBasedInUser(@NotNull UserDomain obj)
	{
		return new Query(Criteria.where("User").is(obj));
	}
	
	protected AnnotationDomain updateUserAndRemove(@NotNull AnnotationDomain obj)
	{
		template.update(UserDomain.class)
		.matching(Criteria.where("_id").is(obj.getUser().getId()))
		.apply(new Update().pull("UserAnnotations",obj))
		.first();
		return obj;
	}
	
	protected AnnotationDomain updateUserAndAnnotation(@NotNull AnnotationDomain obj)
	{
		Query query = new Query(Criteria.where("id").is(obj.getUser().getId())
				.and("UserAnnotations").is(obj.getUser().getUserAnnotations())
				.and("noteId").is(obj.getNoteId()));
		Update update = new Update().set("name", obj.getName())
				.set("value",obj.getValue()).set("description", obj.getDescription());
		this.template.updateFirst(query, update, UserDomain.class);
		return obj;
	}
	
	protected Query idQuery(@NotNull String id)
	{
        return new Query(Criteria.where("noteId").is(id));
	}
	

	@Override
	public List<AnnotationResponse> fetchAnnotationRecent() {
		Query query = fetchBasedInUser(authService.returnUser());
        return template.find(query, AnnotationDomain.class).stream().map(AnnotationResponse::new).toList();
	}

	@Override
	public AnnotationDomain fetchOneById(@NotNull String id) {
		final Query query = idQuery(id);
		return this.template.findOne(query,AnnotationDomain.class);
	}

	@Override
	public AnnotationDomain createAnnotation(@NotNull InputAnnotationRequest input) {
		AnnotationDomain obj = new AnnotationDomain(input,authService.returnUser());
		this.template.save(obj);
		return updateUserAndAdd(obj);
		
	}

	@Override
	public AnnotationDomain updateAnnotation(@NotNull String id, @NotNull InputAnnotationRequest input) {
	AnnotationDomain toUpdate = fetchOneById(id);
		toUpdate.setName(input.getName());
		toUpdate.setValue(input.getValue());
		toUpdate.setDescription(input.getDescription());
		return updateUserAndAnnotation(toUpdate);
	}

	@Override
	public void deleteAnnotation(@NotNull String id) {
		Query query = idQuery(id);
		AnnotationDomain toDelete = template.findOne(query, AnnotationDomain.class);
		if(toDelete != null) {
			this.template.remove(updateUserAndRemove(toDelete));
		}
	}
	
}
