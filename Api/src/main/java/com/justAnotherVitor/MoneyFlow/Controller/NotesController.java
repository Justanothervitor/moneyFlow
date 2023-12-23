package com.justAnotherVitor.MoneyFlow.Controller;
//Classe de recursos relacionados á entidade de notas
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.justAnotherVitor.MoneyFlow.Services.NoteServices;
import com.justAnotherVitor.MoneyFlow.domain.NoteEntity;


@RequestMapping
@RestController(value = "api/users/")
public class NotesController {

	@Autowired
	private NoteServices noteServices;
	
	@PostMapping(value="/notes/")
	public ResponseEntity<NoteEntity> insert(@RequestBody NoteEntity obj) {
		return new ResponseEntity<NoteEntity>(noteServices.insert(obj),HttpStatus.OK);
	}

	
	@GetMapping(value="/notes")
	public ResponseEntity<List<NoteEntity>> findAll() {
		List<NoteEntity> list = this.noteServices.findAll();
		return ResponseEntity.ok().body(list);
	}

	@GetMapping(value="/notes/{id}")
	public ResponseEntity<Optional<NoteEntity>> findById(@PathVariable String id) {
		Optional <NoteEntity> obj = this.noteServices.findById(id);
		return ResponseEntity.ok().body(obj);
	}

	@DeleteMapping(value="/notes/{id}")
	public ResponseEntity<Void> delete(@PathVariable String id) {
		this.noteServices.delete(id);
		return ResponseEntity.noContent().build();
	}

	@PutMapping(value="notes/{id}")
	public ResponseEntity<NoteEntity>update(@PathVariable String id, @RequestBody NoteEntity obj) {
		NoteEntity entity= this.noteServices.update(id,obj);
		return ResponseEntity.ok(entity);
	}

}
