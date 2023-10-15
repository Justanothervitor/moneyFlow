package com.justAnotherVitor.MoneyFlow.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.justAnotherVitor.MoneyFlow.Repository.NotesRepository;
import com.justAnotherVitor.MoneyFlow.Services.Exceptions.DatabaseException;
import com.justAnotherVitor.MoneyFlow.Services.Exceptions.ResourceNotFoundException;
import com.justAnotherVitor.MoneyFlow.domain.NoteEntity;
import com.justAnotherVitor.MoneyFlow.domain.UserEntity;


@Service
public class NoteServices {

	
	@Autowired
	private NotesRepository repository;
	
	
	@Autowired
	private MongoTemplate template;
	
	public NoteEntity insert(NoteEntity obj)
	{
		NoteEntity note = repository.insert(new NoteEntity(obj.getDate(),obj.getAuthor(),obj.getMoney(),obj.getTittle(),obj.getDescription()));
		template.update(UserEntity.class)
		.matching(Criteria.where("_id").is(obj.getAuthor().getId()))
		.apply(new Update().push("Notes").value(note))
		.first();
		
		
		return note;
	}
	
	public List<NoteEntity> findAll()
	{
		return this.repository.findAll();
	}
	
	public Optional<NoteEntity> findById(String id)
	{
		Optional <NoteEntity> note = this.repository.findById(id);
		return note;
	}
	
	public void delete (String id)
	{
		try {
			this.repository.deleteById(id);
		}catch(EmptyResultDataAccessException e){
			throw new ResourceNotFoundException(id);
		}catch(DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}
	}
	
	public NoteEntity update(String id, NoteEntity obj)
	{
		NoteEntity note = this.repository.findById(id).get();
		note.setTittle(obj.getTittle());
		note.setDescription(obj.getDescription());
		note.setMoney(obj.getMoney());
		
		return this.repository.save(note);
	}
		
		}

