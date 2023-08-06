package com.example.backend.service;

import com.example.backend.data.entity.CategoryEntity;
import com.example.backend.data.request.CategoryRequest;
import com.example.backend.repository.CategoryRepository;
import com.github.slugify.Slugify;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;
    public List<CategoryEntity> getAllCategory(){
        List<CategoryEntity> categoryEntities = categoryRepository.findAll();
        return categoryEntities;
    }
    public CategoryEntity createCategory(CategoryRequest categoryRequest) {
        CategoryEntity existed = categoryRepository.findCategoryByName(categoryRequest.getName());
        if(existed != null) return null;
        final Slugify slg = Slugify.builder().build();
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setName(categoryRequest.getName());
        categoryEntity.setSlug(slg.slugify(categoryRequest.getName()));
        categoryRepository.saveAndFlush(categoryEntity);
        return categoryEntity;
    }
    public CategoryEntity updateCategory(int id, CategoryRequest categoryRequest) {
        CategoryEntity existed = categoryRepository.findCategoryByName(categoryRequest.getName());
        if(existed != null) return null;
        CategoryEntity categoryEntity = categoryRepository.findCategoryById(id);
        final Slugify slg = Slugify.builder().build();
        categoryEntity.setName(categoryRequest.getName());
        categoryEntity.setSlug(slg.slugify(categoryRequest.getName()));
        categoryRepository.saveAndFlush(categoryEntity);
        return categoryEntity;
    }
    public CategoryEntity deleteCategory(int id){
        categoryRepository.deleteById(id);
        return null;
    }
    public CategoryEntity getCategoryBySlug(String slug){
        return categoryRepository.findCategoryBySlug(slug);
    }
}
