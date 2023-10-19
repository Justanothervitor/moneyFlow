package com.justAnotherVitor.MoneyFlow.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.justAnotherVitor.MoneyFlow.Repository.NotesRepository;
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
			Optional<NoteEntity> reqNote = repository.findById(id);
			reqNote.orElseThrow(()-> new ResourceNotFoundException(id)).getAuthor().getId();
			NoteEntity toDelNote = reqNote.get();
		this.repository.deleteById(id);
		Update update = new Update();
		update.pull("notes", toDelNote);
		template.update(UserEntity.class)
		.matching(Criteria.where("_id").is(toDelNote.getAuthor().getId()))
		.apply(update)
		.first();

	}
	
	public NoteEntity update(String id, NoteEntity obj)
	{
		NoteEntity note = this.repository.findById(id).get();
		note.setMoney(obj.getMoney());
		note.setTittle(obj.getTittle());
		note.setDescription(obj.getDescription());
		this.repository.save(note);
		Query query = new Query(Criteria.where("_id").is(note.getAuthor().getId()).and("_noteId").is(note.getNoteId()));
		Update update = new Update();
		update.set("money:",note.getMoney()).set("tittle:",note.getTittle()).set("descrption:", note.getDescription());
		//updateAuthor(note);
		template.updateFirst(query, update, UserEntity.class);
		return note ;
	}
	
	/*public NoteEntity updateAuthor(NoteEntity note)
	{
		Query findUser = new Query(Criteria.where("_id").is(note.getAuthor().getId()));
		Query findNote = new Query(Criteria.where("_noteId").is(note.getNoteId()));
		Update update = new Update();
		update.set("Name:",template.findOne(findUser, UserEntity.class).getName());
		String nome = template.findById(note.getAuthor().getId(), UserEntity.class).getName();
		if(nome.equals(note.getAuthor().getName())) {
			template.updateMulti(findNote,update, UserEntity.class);
		}
		return note;
		
		
		
	}*/
		
		}

