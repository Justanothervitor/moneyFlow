package com.justAnotherVitor.MoneyFlow.Resource;
//Classe de recursos relacionados aos  usuários
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

import com.justAnotherVitor.MoneyFlow.Services.ServicesForUser;
import com.justAnotherVitor.MoneyFlow.domain.NoteEntity;
//import com.justAnotherVitor.MoneyFlow.domain.NoteEntity;
import com.justAnotherVitor.MoneyFlow.domain.UserEntity;
import com.justAnotherVitor.MoneyFlow.dto.UserDto;

@RequestMapping
@RestController(value ="/users")
public class UserResource {
	
	@Autowired
	private ServicesForUser service;

	@GetMapping("users")
	public ResponseEntity<List<UserDto>> findAll() {
		List<UserEntity> list = service.findAll();
		List<UserDto> listDto = list.stream().map(x -> new UserDto(x)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDto);
	}

	@GetMapping("users/{id}")
	public ResponseEntity<UserDto>findById(@PathVariable String id) {
		Optional <UserEntity> obj = service.findById(id);
		return ResponseEntity.ok().body(new UserDto(obj));
	}
	
	@GetMapping("users/{id}/notes")
	public ResponseEntity<Optional<List<NoteEntity>>> findNotes(@PathVariable String id) {
		Optional<UserEntity> obj = this.service.findById(id);
		Optional <List<NoteEntity>> notes = obj.stream().map(x-> x.getNotes()).reduce((acc, item) -> {
			acc.addAll(item);
			return acc;
		});
		return ResponseEntity.ok().body(notes);
	}

	@PostMapping("users/")
	public ResponseEntity<UserEntity> insert(@RequestBody UserEntity obj) {
		return new ResponseEntity<UserEntity>(service.insert(obj),HttpStatus.OK);
	}

	@DeleteMapping("users/{id}")
	public ResponseEntity<Void> delete(@PathVariable String id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

	@PutMapping("users/{id}")
	public ResponseEntity<UserEntity>update(@PathVariable String id, @RequestBody UserEntity obj) {
		UserEntity entity= service.update(obj,id);
		return ResponseEntity.ok(entity);
	}
	
}
