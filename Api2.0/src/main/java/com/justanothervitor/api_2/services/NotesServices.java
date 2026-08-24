package com.justanothervitor.api_2.services;

import com.justanothervitor.api_2.config.auth.JwtUtil;
import com.justanothervitor.api_2.models.Category;
import com.justanothervitor.api_2.models.TokenHolder;
import com.justanothervitor.api_2.models.User;
import com.justanothervitor.api_2.models.mappers.NoteMapper;
import com.justanothervitor.api_2.models.payloads.request.NoteCreationRequest;
import com.justanothervitor.api_2.models.payloads.request.NoteUpdateRequest;
import com.justanothervitor.api_2.models.payloads.request.NotesFilterRequest;
import com.justanothervitor.api_2.models.payloads.response.NotePagesResponse;
import com.justanothervitor.api_2.models.payloads.response.NoteResponse;
import com.justanothervitor.api_2.repositories.CategoryRepositories;
import com.justanothervitor.api_2.repositories.NotesRepositories;
import com.justanothervitor.api_2.repositories.UserRepositories;
import com.justanothervitor.api_2.repositories.specification.NotesSpecification;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class NotesServices {

    private static final Set<String> permittedOrderBy = Set.of("id","name","price","createdAt","updateAt");

    private final NotesRepositories notesRepositories;
    private final CategoryRepositories categoryRepositories;
    private final NoteMapper mapper;
    private final TokenHolder tokenHolder;
    private final JwtUtil jwtUtil;
    private final UserRepositories userRepositories;


    @Transactional
    public NotePagesResponse<NoteResponse> list(NotesFilterRequest req)
    {
        var pageable = buildPageable(req);
        var spec = NotesSpecification.withFilters(req);
        var page = notesRepositories.findAll(spec,pageable).map(mapper::toDto);

        return NotePagesResponse.from(page,buildAppliedFilters(req));
    }

    @Transactional
    public NoteResponse findById(Long id)
    {
        return notesRepositories.findById(id).map(mapper::toDto).orElseThrow();
    }


    public NoteResponse create(NoteCreationRequest request)
    {
            Set<Category> categories = solveCategories(request.getTags());
            User user = userRepositories.findByUsername(jwtUtil.extractUsername(tokenHolder.getToken())).orElseThrow();
            var note = mapper.toEntity(request, user, categories);
            var saved = notesRepositories.save(note);
            return mapper.toDto(saved);
    }

    public NoteResponse update(Long id,NoteUpdateRequest request){
        var subject = notesRepositories.findById(id).orElseThrow();

        var categories = solveCategories(request.getTags());
        mapper.updateEntity(subject,request,categories);
        return mapper.toDto(notesRepositories.save(subject));
    }

    public void delete(Long id)
    {
       notesRepositories.deleteById(id);
    }

    private Set<Category> solveCategories(List<String>tags)
    {
        if(tags.isEmpty()) return null;
        Set<Category> categories = new HashSet<>();
        for(String category: tags)
        {
            categories.add(categoryRepositories.findCategoryByName(category).orElseThrow());
        }
        return categories;
    }

    private Pageable buildPageable(NotesFilterRequest request)
    {
        var values = permittedOrderBy.contains(request.getOrderFor()) ? request.getOrderFor() : "name";
        var direction = "description".equals(request.getDirection()) ? Sort.Direction.DESC : Sort.Direction.ASC;

        return PageRequest.of(request.getPage(),request.getSize(),Sort.by(direction,values));
    }

    private Map<String,Object> buildAppliedFilters(NotesFilterRequest request)
    {
        var map = new LinkedHashMap<String,Object>();

        if(request.getSearchContent() != null) map.put("busca",request.getSearchContent());
        if(request.getCategoryId() != null) map.put("categoriaId",request.getCategoryId());
        if(request.getMinPrice() != null) map.put("precoMin",request.getMinPrice());
        if(request.getMaxPrice() != null) map.put("precoMax",request.getMaxPrice());
        map.put("ordenarPor",request.getOrderFor());
        map.put("direcao",request.getDirection());
        return Collections.unmodifiableMap(map);
    }

}
