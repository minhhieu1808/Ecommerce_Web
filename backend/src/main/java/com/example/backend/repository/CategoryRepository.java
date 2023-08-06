package com.example.backend.repository;

import com.example.backend.data.entity.CategoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Integer> {
    Page<CategoryEntity> findAll(Pageable pageable);

    List<CategoryEntity> findAll();

    CategoryEntity findCategoryByName(String name);
    CategoryEntity findCategoryBySlug(String slug);
    CategoryEntity findCategoryById(int id);

    void deleteById(int id);
}
