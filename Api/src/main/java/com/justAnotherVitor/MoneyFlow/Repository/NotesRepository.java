package com.justAnotherVitor.MoneyFlow.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.justAnotherVitor.MoneyFlow.domain.NoteProperties;

public interface NotesRepository extends MongoRepository<NoteProperties,String>{

}
