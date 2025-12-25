package com.justanothervitor.api_2.repositories;

import com.justanothervitor.api_2.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepositories extends JpaRepository<Category, Integer> {

    Category findCategoryById(Integer id);
}
