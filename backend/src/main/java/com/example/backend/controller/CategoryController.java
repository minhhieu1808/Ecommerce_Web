package com.example.backend.controller;

import com.example.backend.data.entity.CategoryEntity;
import com.example.backend.data.response.base.MyResponse;
import com.example.backend.data.request.CategoryRequest;
import com.example.backend.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
public class CategoryController {
    @Autowired
    CategoryService categoryService;
    @RequestMapping(value = "/api/v1/category/get-category", method = RequestMethod.GET)
    public ResponseEntity<?> getAllCategory() {
        List<CategoryEntity> categoryEntities = categoryService.getAllCategory();
        MyResponse response = MyResponse
                .builder()
                .buildCode(200)
                .buildMessage("Successfully")
                .buildData(categoryEntities)
                .buildSuccess(true)
                .get();
        return ResponseEntity.ok(response);
    }
    @RequestMapping(value = "/api/v1/category/create-category", method = RequestMethod.POST)
    public ResponseEntity<?> createCategory(@RequestBody CategoryRequest categoryRequest) {
        CategoryEntity categoryEntity = categoryService.createCategory(categoryRequest);
        if(categoryEntity == null){
            MyResponse response = MyResponse
                    .builder()
                    .buildCode(200)
                    .buildMessage("Failed")
                    .buildData(null)
                    .buildSuccess(false)
                    .get();
            return ResponseEntity.ok(response);

        }
        MyResponse response = MyResponse
                .builder()
                .buildCode(200)
                .buildMessage("Successfully")
                .buildData(categoryEntity)
                .buildSuccess(true)
                .get();
        return ResponseEntity.ok(response);
    }
    @RequestMapping(value = "/api/v1/category/update-category/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateCategory(@PathVariable int id, @RequestBody CategoryRequest categoryRequest) {
        CategoryEntity categoryEntity = categoryService.updateCategory(id, categoryRequest);
        if(categoryEntity == null){
            MyResponse response = MyResponse
                    .builder()
                    .buildCode(200)
                    .buildMessage("Failed")
                    .buildData(null)
                    .buildSuccess(false)
                    .get();
            return ResponseEntity.ok(response);

        }
        MyResponse response = MyResponse
                .builder()
                .buildCode(200)
                .buildMessage("Successfully")
                .buildData(categoryEntity)
                .buildSuccess(true)
                .get();
        return ResponseEntity.ok(response);
    }
    @RequestMapping(value = "/api/v1/category/delete-category/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteCategory(@PathVariable int id) {
        CategoryEntity categoryEntity = categoryService.deleteCategory(id);
        MyResponse response = MyResponse
                .builder()
                .buildCode(200)
                .buildMessage("Successfully")
                .buildData(categoryEntity)
                .buildSuccess(true)
                .get();
        return ResponseEntity.ok(response);
    }
}
