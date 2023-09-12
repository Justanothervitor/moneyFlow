package com.justAnotherVitor.MoneyFlow.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.justAnotherVitor.MoneyFlow.Repository.NotesRepository;
import com.justAnotherVitor.MoneyFlow.domain.NoteProperties;

@Service
public class NoteServices {

	
	@Autowired
	private NotesRepository repository;
	
	public List<NoteProperties> findAll()
	{
		return repository.findAll();
		
	}
	
	public NoteProperties findById(Long id)
	{
		Optional<NoteProperties> obj = repository.findById(id);
		return obj.get();
	}
	
}
