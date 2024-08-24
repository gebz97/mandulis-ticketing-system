package org.mandulis.mts.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryResponse> findAll() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(CategoryService::convertEntityToDto).toList();
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

    public Optional<CategoryResponse> update(Long id, CategoryRequest request) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isPresent()) {
            Category category = optionalCategory.get();
            category.setName(request.getName());
            category.setDescription(request.getDescription());
            Category updatedCategory = categoryRepository.save(category);
            return Optional.of(convertEntityToDto(updatedCategory));
        } else {
            return Optional.empty();
        }
    }

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
}
