package com.justAnotherVitor.MoneyFlow.Resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.justAnotherVitor.MoneyFlow.Services.NoteServices;
import com.justAnotherVitor.MoneyFlow.domain.NoteProperties;

@RestController
@RequestMapping(value = "/usernotes")
public class NoteResource {

	@Autowired
	private NoteServices noteFinder;
	
	@GetMapping
	public ResponseEntity<List<NoteProperties>> findAll()
	{
		List<NoteProperties> list = noteFinder.findAll();
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<NoteProperties> findById(@PathVariable Long id)
	{
		NoteProperties obj = noteFinder.findById(id);
		return ResponseEntity.ok().body(obj);
	}
	
}
