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
import com.Api.MoneyFlow.payloads.request.InputAnnotationRequest;
import com.Api.MoneyFlow.payloads.response.MessageResponse;

@RestController
@RequestMapping("api/data")
@CrossOrigin(origins={"http://localhost:4200"},maxAge=3600,allowCredentials="true")
public class AnnotationsController {

	@Autowired
	private AnnotationsRepositories annotationsRepo;
	
	@GetMapping("/recent")
	public ResponseEntity<List<AnnotationDomain>> getRecentAnnotations()
	{
		return ResponseEntity.ok().body(annotationsRepo.fecthAnnotationRecent());
	}

	/*@GetMapping("/{id}")
	public ResponseEntity<AnnotationDomain> getOneById(@RequestBody String id)
	{
		return ResponseEntity.ok().body(annotationsRepo.fetchOneById(id));
	}*/
	
	@PostMapping("/add")
	public ResponseEntity<AnnotationDomain> createAnnotation(@RequestBody InputAnnotationRequest input)
	{
		return ResponseEntity.ok(annotationsRepo.createAnnotation(input));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<AnnotationDomain> updateAnnotation(@RequestBody String id, @RequestBody InputAnnotationRequest input)
	{
		return ResponseEntity.ok().body(annotationsRepo.updateAnnotation(id, input));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteAnnotation(@RequestBody String id)
	{
		annotationsRepo.deleteAnnotation(id);
		return ResponseEntity.ok().body(new MessageResponse("Annotation Deleted!"));
	}
}
