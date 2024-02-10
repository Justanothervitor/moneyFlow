package com.Api.MoneyFlow.Repositories;

import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.Api.MoneyFlow.Templates.AnnotationTemplate;
import com.Api.MoneyFlow.domains.AnnotationDomain;
import com.Api.MoneyFlow.domains.UserDomain;

@Repository
public class AnnotationsRepositories implements AnnotationTemplate{

	@Autowired
	private MongoTemplate template;
	
	private Query dataQuery ()
	{
		ZonedDateTime value = ZonedDateTime.now();
		Query query = new Query(Criteria.where("dateOfCreation").is(value));
		return query;
	}

	private void updateUser(AnnotationDomain obj)
	{
		template.update(UserDomain.class)
		.matching(Criteria.where("id").is(obj.getUser().getId()))
		.apply(new Update().push("annotations").value(obj));
		
	}
	
	private AnnotationDomain updateUserAndAnnotation(AnnotationDomain obj)
	{
		Query query = new Query(Criteria.where("id").is(obj.getUser().getId())
				.and("annotations").is(obj.getUser().getAnnotations())
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
	public List<AnnotationDomain> fetchAnnotations() {
		
		return this.template.findAll(AnnotationDomain.class);
	}

	@Override
	public List<AnnotationDomain> fecthAnnotationRecent() {
		Query query = dataQuery();
		return this.template.find(query, AnnotationDomain.class);
	}

	@Override
	public AnnotationDomain fetchOneById(String id) {
		Query query = idQuery(id);
		return this.template.findOne(query,AnnotationDomain.class);
	}

	@Override
	public AnnotationDomain createAnnotation(AnnotationDomain obj) {
		updateUser(obj);
		return this.template.save(obj);
	}

	@Override
	public AnnotationDomain updateAnnotation(String id,AnnotationDomain obj) {
	AnnotationDomain toUpdate = fetchOneById(id);
		toUpdate.setName(obj.getName());
		toUpdate.setValue(obj.getValue());
		toUpdate.setDescription(obj.getDescription());
		return updateUserAndAnnotation(toUpdate);
	}

	@Override
	public void deleteAnnotation(String id) {
		Query query = idQuery(id);
		this.template.findAndRemove(query, AnnotationDomain.class);
		
	}

}
