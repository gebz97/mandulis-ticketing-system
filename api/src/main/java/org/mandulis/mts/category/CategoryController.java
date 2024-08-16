package org.mandulis.mts.category;

import org.mandulis.mts.rest.ApiResponse;
import org.mandulis.mts.rest.ResponseHandler;
import org.mandulis.mts.rest.SuccessMessages;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<ApiResponse<List<CategoryResponse>>> getAllCategories() {
        return ResponseHandler.handleSuccess(
                categoryService.findAll(),
                HttpStatus.OK,
                SuccessMessages.QUERY_SUCCESSFUL
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryResponse>> getCategoryById(@PathVariable Long id) {
        return categoryService.getResponseById(id)
                .map(response -> ResponseHandler.handleSuccess(
                        response, HttpStatus.OK, SuccessMessages.QUERY_SUCCESSFUL
                ))
                .orElseGet(() -> ResponseHandler.handleError(
                        null, HttpStatus.NOT_FOUND, "Category not found", List.of("Category not found")
                ));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CategoryResponse>> createCategory(@RequestBody CategoryRequest request) {
        return ResponseHandler.handleSuccess(
                categoryService.save(request),
                HttpStatus.CREATED,
                "Category created successfully"
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryResponse>> updateCategory(
            @PathVariable Long id, @RequestBody CategoryRequest request
    ) {
        return categoryService.update(id, request)
                .map(response -> ResponseHandler.handleSuccess(
                        response, HttpStatus.OK, "Category updated successfully"
                ))
                .orElseGet(() -> ResponseHandler.handleError(
                        null, HttpStatus.NOT_FOUND, "Category not found", List.of("Category not found")
                ));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCategory(@PathVariable Long id) {
        categoryService.deleteById(id);
        return ResponseHandler.handleSuccess(null, HttpStatus.NO_CONTENT, "Category deleted successfully");
    }
}
