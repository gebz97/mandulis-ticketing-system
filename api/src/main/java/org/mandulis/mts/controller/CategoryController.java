package org.mandulis.mts.controller;

import org.mandulis.mts.dto.request.CategoryRequest;
import org.mandulis.mts.dto.response.CategoryResponse;
import org.mandulis.mts.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/public/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(final CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getAllCategories() {
        List<CategoryResponse> categories = categoryService.findAll();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/paginate")
    public Page<CategoryResponse> getAllCategories(Pageable pageable) {
        Page<CategoryResponse> categories = categoryService.findAll(pageable);
        return categories;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable Long id) {
        Optional<CategoryResponse> category = categoryService.getResponseById(id);
        return category.map(ResponseEntity::ok)
                .orElseGet(() -> {
                    //Map<String, String> response = new HashMap<>();
                    //response.put("error", "Category not found");
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
                });
    }

    @PostMapping
    public ResponseEntity<CategoryResponse> createCategory(@RequestBody CategoryRequest request) {
        try {
            CategoryResponse category = categoryService.save(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(category);
        } catch (Exception e) {
            //Map<String, String> response = new HashMap<>();
            //response.put("error", "Error creating category");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> updateCategory(@PathVariable Long id, @RequestBody CategoryRequest request) {
        try {
            CategoryResponse category = categoryService.update(id, request);
            return ResponseEntity.ok(category);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Category not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteCategory(@PathVariable Long id) {
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
