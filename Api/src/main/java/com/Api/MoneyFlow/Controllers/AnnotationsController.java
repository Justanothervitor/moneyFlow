package com.Api.MoneyFlow.Controllers;

import com.Api.MoneyFlow.Domains.Exceptions.AnnotationNotFound;
import com.Api.MoneyFlow.Payloads.Request.InputAnnotationPutRequest;
import com.Api.MoneyFlow.Repositories.AnnotationsRepositories;
import com.Api.MoneyFlow.Payloads.Request.InputAnnotationRequest;
import com.Api.MoneyFlow.Payloads.Response.AnnotationResponse;
import com.Api.MoneyFlow.SecurityServices.AuthServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
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

    @Autowired
    protected AuthServiceImpl authService;
	
	@GetMapping("/recent")
	public ResponseEntity<List<AnnotationResponse>> getRecentAnnotations(HttpServletRequest request)
	{
        var user = authService.getCurrentLoggedUser(request.getHeader("Authorization").substring(7));
		return ResponseEntity.ok().body(annotationsRepo.fetchAnnotationRecent(user));
	}

	@GetMapping("/{id}")
	public ResponseEntity<AnnotationResponse> getOneById(@PathVariable final String id) throws AnnotationNotFound
	{
        AnnotationResponse response = new AnnotationResponse(this.annotationsRepo.fetchOneById(id));
        return ResponseEntity.ok().body(response);
    }
	
	@PostMapping("/add")
	public ResponseEntity<?> createAnnotation(HttpServletRequest request, @RequestBody final InputAnnotationRequest input)
	{
        var user = authService.getCurrentLoggedUser(request.getHeader("Authorization"));
        this.annotationsRepo.createAnnotation(input,user);
		return ResponseEntity.ok().build();
	}

	/*@PostMapping("/search")
	public ResponseEntity<List<AnnotationResponse>> searchAnnotation(@RequestBody final String requiredName)
	{
		return ResponseEntity.ok().build();
	}*/
	
	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateAnnotation(@PathVariable final String id, @RequestBody final InputAnnotationPutRequest input)
	{
        this.annotationsRepo.updateAnnotation(id, input);
		return ResponseEntity.accepted().build();
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteAnnotation(@PathVariable final String id)
	{
		this.annotationsRepo.deleteAnnotation(id);
		return ResponseEntity.noContent().build();
	}
}
//new MessageResponse("Annotation Deleted!")