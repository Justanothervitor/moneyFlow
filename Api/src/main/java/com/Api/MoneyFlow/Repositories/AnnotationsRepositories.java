package com.Api.MoneyFlow.Repositories;

import java.util.List;
import java.util.Objects;

import com.Api.MoneyFlow.Payloads.Request.InputAnnotationPutRequest;
import com.Api.MoneyFlow.Payloads.Response.AnnotationResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.Api.MoneyFlow.Templates.AnnotationTemplate;
import com.Api.MoneyFlow.Domains.AnnotationDomain;
import com.Api.MoneyFlow.Domains.UserDomain;
import com.Api.MoneyFlow.Payloads.Request.InputAnnotationRequest;

@Repository
public class AnnotationsRepositories implements AnnotationTemplate{

	protected MongoTemplate template;

    @Autowired
    public AnnotationsRepositories(MongoTemplate template) {
        this.template = Objects.requireNonNull(template,"template is required");
    }

	protected void updateUserAndAdd(@NotNull AnnotationDomain obj)
	{
		template.update(UserDomain.class)
		.matching(Criteria.where("_id").is(obj.getUser().getId()))
		.apply(new Update().push("UserAnnotations").value(obj))
		.first();
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
	
	protected void updateUserAndAnnotation(@NotNull AnnotationDomain obj)
	{
		Query query = new Query(Criteria.where("id").is(obj.getUser().getId())
				.and("UserAnnotations").is(obj.getUser().getUserAnnotations())
				.and("noteId").is(obj.getNoteId()));
		Update update = new Update().set("name", obj.getName())
				.set("value",obj.getValue()).set("description", obj.getDescription());
		this.template.updateFirst(query, update, UserDomain.class);
	}
	
	protected Query idQuery(@NotNull String id)
	{
        return new Query(Criteria.where("noteId").is(id));
	}

	/*protected Query nameQuery(@NotNull String request){
		return new Query(Criteria.where("name"))
	}*/


	@Override
	public List<AnnotationResponse> fetchAnnotationRecent(UserDomain requested) {
		Query query = fetchBasedInUser(requested);
        List<AnnotationResponse> response = template.find(query, AnnotationDomain.class).stream().map(AnnotationResponse::new).toList();
        return response;
	}

	@Override
	public AnnotationDomain fetchOneById(@NotNull String id){
		final Query query = idQuery(id);
        return this.template.findOne(query, AnnotationDomain.class);
	}

	@Override
	public void createAnnotation(@NotNull InputAnnotationRequest input,UserDomain requested) {
		AnnotationDomain obj = new AnnotationDomain(input,requested);
		this.template.save(obj);
		updateUserAndAdd(obj);
	}

	@Override
	public void updateAnnotation(@NotNull String id, @NotNull InputAnnotationPutRequest input) {
	AnnotationDomain toUpdate = fetchOneById(id);
		toUpdate.setName(input.getName());
		toUpdate.setValue(input.getValue());
		toUpdate.setDescription(input.getDescription());
        updateUserAndAnnotation(toUpdate);
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
