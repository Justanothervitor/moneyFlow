package com.justAnotherVitor.MoneyFlow.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.justAnotherVitor.MoneyFlow.domain.NoteEntity;

@Repository
public interface NotesRepository extends MongoRepository<NoteEntity,String>{
	
}
