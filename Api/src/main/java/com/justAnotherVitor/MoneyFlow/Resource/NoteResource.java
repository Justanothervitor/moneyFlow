package com.justAnotherVitor.MoneyFlow.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.justAnotherVitor.MoneyFlow.Services.NoteServices;
import com.justAnotherVitor.MoneyFlow.domain.NoteEntity;


@RequestMapping
@RestController(value = "/notes")
public class NoteResource {

	@Autowired
	private NoteServices noteServices;
	
	@PostMapping(value="/notes/")
	public ResponseEntity<NoteEntity> createANewNote(@RequestBody NoteEntity obj,String id) {
		return new ResponseEntity<NoteEntity>(noteServices.createANewNote(obj,id),HttpStatus.OK);
	}

	
	/*@GetMapping("notes")
	public ResponseEntity<List<NoteEntity>> findAll() {
		List<NoteEntity> list = this.noteServices.findAll();
		return ResponseEntity.ok().body(list);
	}

	@GetMapping("notes/{id}")
	public ResponseEntity<Optional<NoteEntity>> findById(@PathVariable String id) {
		Optional <NoteEntity> obj = this.noteServices.findById(id);
		return ResponseEntity.ok().body(obj);
	}

	@DeleteMapping("notes/{id}")
	public ResponseEntity<Void> delete(@PathVariable String id) {
		this.noteServices.delete(id);
		return ResponseEntity.noContent().build();
	}

	@PutMapping("notes/{id}")
	public ResponseEntity<NoteEntity>update(@PathVariable String id, @RequestBody NoteEntity obj) {
		NoteEntity entity= this.noteServices.update(id,obj);
		return ResponseEntity.ok(entity);
	}
*/
}
