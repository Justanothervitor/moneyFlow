package com.Api.MoneyFlow.Controllers;

import com.Api.MoneyFlow.Repositories.AnnotationsRepositories;
import com.Api.MoneyFlow.Domains.AnnotationDomain;
import com.Api.MoneyFlow.Payloads.Request.InputAnnotationRequest;
import com.Api.MoneyFlow.Payloads.Response.AnnotationResponse;
import com.Api.MoneyFlow.Payloads.Response.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/data")
@CrossOrigin(origins={"http://localhost:4200"},allowCredentials="true")
public class AnnotationsController {

	@Autowired
	protected AnnotationsRepositories annotationsRepo;
	
	@GetMapping("/recent")
	public ResponseEntity<List<AnnotationResponse>> getRecentAnnotations()
	{
		return ResponseEntity.ok().body(annotationsRepo.fetchAnnotationRecent());
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
