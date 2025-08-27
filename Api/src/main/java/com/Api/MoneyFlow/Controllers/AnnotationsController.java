package com.Api.MoneyFlow.Controllers;

import com.Api.MoneyFlow.Payloads.Request.InputAnnotationPutRequest;
import com.Api.MoneyFlow.Repositories.AnnotationsRepositories;
import com.Api.MoneyFlow.Domains.AnnotationDomain;
import com.Api.MoneyFlow.Payloads.Request.InputAnnotationRequest;
import com.Api.MoneyFlow.Payloads.Response.AnnotationResponse;
import com.Api.MoneyFlow.Payloads.Response.MessageResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/data")
@CrossOrigin(origins={"http://localhost:46649","https://piranha-wanted-shark.ngrok-free.app"},allowCredentials = "true")
public class AnnotationsController {

	@Autowired
	protected AnnotationsRepositories annotationsRepo;
	
	@GetMapping("/recent")
	public ResponseEntity<List<AnnotationResponse>> getRecentAnnotations()
	{
		return ResponseEntity.ok().body(annotationsRepo.fetchAnnotationRecent());
	}

	@GetMapping("/{id}")
	public ResponseEntity<AnnotationDomain> getOneById(@PathVariable final String id)
	{
		return ResponseEntity.ok().body(annotationsRepo.fetchOneById(id));
	}
	
	@PostMapping("/add")
	public ResponseEntity<AnnotationDomain> createAnnotation(@RequestBody final InputAnnotationRequest input)
	{
		return ResponseEntity.ok(annotationsRepo.createAnnotation(input));
	}

	/*@PostMapping("/search")
	public ResponseEntity<List<AnnotationResponse>> searchAnnotation(@RequestBody final String requiredName)
	{
		return ResponseEntity.ok().build();
	}*/
	
	@PutMapping("/update/{id}")
	public ResponseEntity<AnnotationDomain> updateAnnotation(@PathVariable final String id, @RequestBody final InputAnnotationPutRequest input)
	{
		return ResponseEntity.ok().body(annotationsRepo.updateAnnotation(id, input));
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteAnnotation(@PathVariable final String id)
	{
		annotationsRepo.deleteAnnotation(id);
		return ResponseEntity.noContent().build();
	}
}
//new MessageResponse("Annotation Deleted!")