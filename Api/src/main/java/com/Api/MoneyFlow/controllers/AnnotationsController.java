package com.Api.MoneyFlow.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Api.MoneyFlow.Repositories.AnnotationsRepositories;
import com.Api.MoneyFlow.domains.AnnotationDomain;
import com.Api.MoneyFlow.payloads.response.MessageResponse;

@RestController
@RequestMapping("api/data")
@CrossOrigin(origins="localhost:4200",maxAge=3600,allowCredentials="true")
public class AnnotationsController {

	@Autowired
	private AnnotationsRepositories annotationsRepo;
	
	@GetMapping("/home")
	public ResponseEntity<List<AnnotationDomain>> getRecentAnnotations()
	{
		return ResponseEntity.ok(annotationsRepo.fecthAnnotationRecent());
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<AnnotationDomain>> getAllAnnotations()
	{
		return ResponseEntity.ok(annotationsRepo.fetchAnnotations());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<AnnotationDomain> getOneById(@RequestBody String id)
	{
		return ResponseEntity.ok(annotationsRepo.fetchOneById(id));
	}
	
	@PostMapping("/add")
	public ResponseEntity<AnnotationDomain> createAnnotation(@RequestBody AnnotationDomain obj)
	{
		return ResponseEntity.ok(annotationsRepo.createAnnotation(obj));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<AnnotationDomain> updateAnnotation(@RequestBody String id, AnnotationDomain obj)
	{
		return ResponseEntity.ok(annotationsRepo.updateAnnotation(id, obj));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteAnnotation(@RequestBody String id)
	{
		annotationsRepo.deleteAnnotation(id);
		return ResponseEntity.ok(new MessageResponse("Annotation Deleted!"));
	}
}
