package com.justanothervitor.api_2.models.mappers;

import com.justanothervitor.api_2.models.Category;
import com.justanothervitor.api_2.models.Notes;
import com.justanothervitor.api_2.models.User;
import com.justanothervitor.api_2.models.payloads.request.NoteCreationRequest;
import com.justanothervitor.api_2.models.payloads.request.NoteUpdateRequest;
import com.justanothervitor.api_2.models.payloads.response.NoteResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class NoteMapper {

    public NoteResponse toDto(Notes n)
    {
        return new NoteResponse(n.getId(),
                n.getName(),
                n.getDescription(),
                n.getPrice(),
                n.getTags().stream().map(Category::getName).toList()
                );
    }

    public Notes toEntity(NoteCreationRequest req, User author, Set<Category> tags){
        return new Notes(
                req.getName(),
                req.getDescription(),
                req.getPrice(),

                author,
                tags
        );
    }

    public void updateEntity(Notes subject, NoteUpdateRequest req, Set<Category> tags){
        subject.setName(req.getName());
        subject.setDescription(req.getDescription());
        subject.setPrice(req.getPrice());
        subject.setTags(tags);
    }
}
