package com.justAnotherVitor.MoneyFlow.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.justAnotherVitor.MoneyFlow.domain.NoteEntity;

public interface NotesRepository extends MongoRepository<NoteEntity,String>{
	

}
