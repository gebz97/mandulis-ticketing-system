package org.mandulis.mts.controller;

import org.mandulis.mts.dto.request.CategoryRequest;
import org.mandulis.mts.dto.request.PageRequestParams;
import org.mandulis.mts.dto.response.CategoryResponse;
import org.mandulis.mts.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/user/category")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(final CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    @Transactional(readOnly = true)
    public Page<CategoryResponse> getAllCategories(@ModelAttribute PageRequestParams pageRequestParams) {
        Page<CategoryResponse> categories = categoryService.findAll(pageRequestParams);
        return categories;
    }

    @GetMapping(value = "/id={id}")
    @Transactional(readOnly = true)
    public ResponseEntity<?> getCategoryById(@PathVariable("id") Long id) {
        Optional<CategoryResponse> category = categoryService.getResponseById(id);
        if (category.isPresent()) {
            return ResponseEntity.ok(category.get());
        } else {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Category not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PostMapping
    public ResponseEntity<?> createCategory(@RequestBody CategoryRequest request) {
        try {
            CategoryResponse category = categoryService.save(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(category);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Error creating category");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PutMapping(value = "/id={id}")
    public ResponseEntity<?> updateCategory(@PathVariable("id") Long id, @RequestBody CategoryRequest request) {
        try {
            CategoryResponse category = categoryService.update(id, request);
            return ResponseEntity.ok(category);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Category not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @DeleteMapping(value = "/id={id}")
    public ResponseEntity<?> deleteCategory(@PathVariable("id") Long id) {
        try {
            categoryService.deleteById(id);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Category deleted successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Category not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}