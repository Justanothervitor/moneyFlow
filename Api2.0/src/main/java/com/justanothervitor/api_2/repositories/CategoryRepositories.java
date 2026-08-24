package com.justanothervitor.api_2.repositories;

import com.justanothervitor.api_2.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepositories extends JpaRepository<Category, Integer> {

    Optional<Category> findCategoryById(Integer id);
    List<Category> findAll();

   Optional<Category> findCategoryByName(String name);
}
