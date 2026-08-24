package com.justanothervitor.api_2.repositories;

import com.justanothervitor.api_2.models.Notes;
import com.justanothervitor.api_2.models.payloads.request.UpdateNotePayload;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface NotesRepositories extends JpaRepository<Notes,Long>, JpaSpecificationExecutor<Notes> {

    Optional<Notes> findByName(String name);

    Page<Notes> findByCategoryId(int id, Pageable pageable);

    @Query("SELECT n FROM notes n WHERE n.price BETWEEN :minPrice and :maxPrice")
    Page<Notes> findByPrice(@Param("minPrice")BigDecimal minPrice, @Param("maxPrice")BigDecimal maxPrice, Pageable pageable);

    @Query("SELECT n FROM notes n WHERE (LOWER(n.name)) LIKE LOWER(CONCAT('%',:searchContent,'%')) OR LOWER(n.description) LIKE LOWER(CONCAT('%',:searchContent,'%'))")
    Page<Notes> search(@Param("searchContent")String searchContent, Pageable pageable);

}
