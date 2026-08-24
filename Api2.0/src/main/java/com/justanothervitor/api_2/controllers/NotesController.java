package com.justanothervitor.api_2.controllers;

import com.justanothervitor.api_2.models.payloads.request.NoteCreationRequest;
import com.justanothervitor.api_2.models.payloads.request.NoteUpdateRequest;
import com.justanothervitor.api_2.models.payloads.request.NotesFilterRequest;
import com.justanothervitor.api_2.models.payloads.response.NotePagesResponse;
import com.justanothervitor.api_2.models.payloads.response.NoteResponse;
import com.justanothervitor.api_2.services.NotesServices;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/data/notes")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class NotesController {


    protected final NotesServices notesServices;

    @PostMapping("/search")
    public ResponseEntity<NotePagesResponse> search(@Valid @RequestBody NotesFilterRequest request)
    {
        return ResponseEntity.ok().body(this.notesServices.list(request));
    }

    @PostMapping("/create")
    public ResponseEntity<NoteResponse> create(@Valid @RequestBody NoteCreationRequest request)
    {
        return ResponseEntity.ok().body(this.notesServices.create(request));
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<NoteResponse> getById(@PathVariable Long id)
    {
        return ResponseEntity.ok().body(this.notesServices.findById(id));
    }

    @PutMapping("/get/{id}/update")
    public ResponseEntity<NoteResponse> updateNote(@PathVariable Long id, NoteUpdateRequest request)
    {
        return ResponseEntity.ok().body(this.notesServices.update(id,request));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteNote(@PathVariable Long id)
    {
        this.notesServices.delete(id);
        return ResponseEntity.noContent().build();
    }


}
