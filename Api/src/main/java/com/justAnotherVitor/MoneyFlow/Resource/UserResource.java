package com.justAnotherVitor.MoneyFlow.Resource;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
/*import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;*/
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.justAnotherVitor.MoneyFlow.Services.ServicesForUser;
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
/*
	@PostMapping
	public ResponseEntity<UserProperties> insert(@RequestBody UserProperties obj) {
		obj = userServices.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();

		return ResponseEntity.created(uri).body(obj);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		userServices.delete(id);
		return ResponseEntity.noContent().build();
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<UserProperties> update(@PathVariable Long id, @RequestBody UserProperties obj) {
		obj = userServices.update(id, obj);
		return ResponseEntity.ok().body(obj);
	}
*/
}
