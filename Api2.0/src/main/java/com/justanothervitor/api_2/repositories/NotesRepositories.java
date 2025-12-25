package com.justanothervitor.api_2.repositories;

import com.justanothervitor.api_2.models.Notes;
import com.justanothervitor.api_2.models.payloads.request.UpdateNotePayload;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotesRepositories extends JpaRepository<Notes, Long> {
    void deleteNotesById(long id);

}
