package org.mandulis.mts.service;

import org.mandulis.mts.dto.request.CategoryRequest;
import org.mandulis.mts.dto.response.CategoryResponse;
import org.mandulis.mts.entity.Category;
import org.mandulis.mts.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Transactional(readOnly = true)
    public List<CategoryResponse> findAll() {
        List<CategoryResponse> categories = categoryRepository.findAll().stream().map(CategoryService::convertEntityToDto).toList();
        return categories;
    }

    @Transactional(readOnly = true)
    public Page<CategoryResponse> findAll(Pageable pageable) {
        Page<CategoryResponse> categories = categoryRepository.findAll(pageable)
                .map(CategoryService::convertEntityToDto);
        return categories;
    }

    public CategoryResponse save(CategoryRequest request) {
        Category category = new Category();
        category.setName(request.getName());
        category.setDescription(request.getDescription());
        Category savedCategory = categoryRepository.save(category);
        return convertEntityToDto(savedCategory);
    }

    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }

    public CategoryResponse update(Long id, CategoryRequest request) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isPresent()) {
            Category category = optionalCategory.get();
            category.setName(request.getName());
            category.setDescription(request.getDescription());
            Category updatedCategory = categoryRepository.save(category);
            return convertEntityToDto(updatedCategory);
        } else {
            throw new RuntimeException("Category not found");
        }
    }

    @Transactional(readOnly = true)
    public Optional<CategoryResponse> getResponseById(Long id) {
        return categoryRepository.findById(id).map(CategoryService::convertEntityToDto);
    }

    public static CategoryResponse convertEntityToDto(Category category) {
        var newCategory = new CategoryResponse();
        newCategory.setName(category.getName());
        newCategory.setId(category.getId());
        newCategory.setDescription(category.getDescription());
        return newCategory;
    }
    /*
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }

    public Category update(Category category) {
        return categoryRepository.save(category);
    }

    public CategoryResponse createCategory(CategoryRequest request) {
        Category category = new Category();
        category.setName(request.getName());
        category.setDescription(request.getDescription());
        //TODO:
        return null;
    }

    public Optional<CategoryResponse> getResponseById(Long id) {
        return categoryRepository.findById(id).map(CategoryService::convertEntityToDto);
    }

    public static CategoryResponse convertEntityToDto(Category category) {
        var newCategory = new CategoryResponse();
        newCategory.setName(category.getName());
        newCategory.setId(category.getId());
        return newCategory;
    }
    public static Category convertDtoToEntity(CategoryResponse categoryResponse) {
        var category = new Category();
        category.setId(categoryResponse.getId());
        category.setName(categoryResponse.getName());
        return category;
    }

     */
}
