package org.mandulis.mts.category;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    @Nested
    class FindAll {

        @Test
        void CategoryService_FindAllWhenInitialized() {
            List<Category> categories = CategoryHelperFactory.listOfCategories();
            when(categoryRepository.findAll()).thenReturn(categories);

            List<CategoryResponse> categoryList = categoryService.findAll();

            assertFalse(categoryList.isEmpty());
            assertEquals(2, categoryList.size());
            assertEquals(CategoryHelperFactory.defaultCategory().getName(), categories.getFirst().getName());
        }

        @Test
        void CategoryService_FindAllWhenNotInitialized() {
            when(categoryRepository.findAll()).thenReturn(Collections.emptyList());

            List<CategoryResponse> categories = categoryService.findAll();

            assertTrue(categories.isEmpty());
            assertNotNull(categories);
        }

    }

    @Nested
    class Save {

        @Test
        void CategoryService_ShouldSaveWhenNameNotExists() {
            CategoryRequest categoryRequest = CategoryHelperFactory.makeCategoryRequest();
            when(categoryRepository.findByName(categoryRequest.getName())).thenReturn(Optional.empty());
            Category category = CategoryHelperFactory.defaultCategory();
            when(categoryRepository.save(any(Category.class))).thenReturn(category);

            Optional<CategoryResponse> optionalCategoryResponse = categoryService.save(categoryRequest);

            assertTrue(optionalCategoryResponse.isPresent());
            assertNotNull(optionalCategoryResponse.get().getName());
            assertEquals(categoryRequest.getName(), optionalCategoryResponse.get().getName());
            verify(categoryRepository).save(any(Category.class));
        }

        @Test
        void CategoryService_ShouldNotSaveWhenNameExists() {
            CategoryRequest category = CategoryHelperFactory.makeCategoryRequest();
            when(categoryRepository.findByName(category.getName())).thenReturn(Optional.of(new Category()));

            Optional<CategoryResponse> optionalCategory = categoryService.save(category);

            assertTrue(optionalCategory.isEmpty());
            verify(categoryRepository, never()).save(any(Category.class));
        }

    }

    @Nested
    class DeleteById {

        @Test
        void CategoryService_DeleteByIdWhenIdNotExists_ShouldIgnoreIt() {
            Long randomId = 999L;
            doNothing().when(categoryRepository).deleteById(randomId);

            assertDoesNotThrow(() -> categoryService.deleteById(randomId));

            verify(categoryRepository, times(1)).deleteById(randomId);
        }

        @Test
        void CategoryService_DeleteByIdWhenExists() {
            Category category = CategoryHelperFactory.defaultCategory();
            Long id = category.getId();
            categoryRepository.save(category);

            categoryService.deleteById(id);
            Optional<Category> byId = categoryRepository.findById(id);

            assertTrue(byId.isEmpty());
            verify(categoryRepository, times(1)).deleteById(id);
        }

    }

    @Nested
    class Update {

        @Test
        void CategoryService_ShouldUpdate_WhenNameNotExists() {
            CategoryRequest categoryRequest = CategoryHelperFactory.makeCategoryRequest();
            Category category = CategoryHelperFactory.defaultCategory();
            String name = categoryRequest.getName();
            Long id = category.getId();
            when(categoryRepository.existsByNameAndIdNot(name, category.getId())).thenReturn(false);
            when(categoryRepository.save(any(Category.class))).thenReturn(category);
            when(categoryRepository.findById(id)).thenReturn(Optional.of(category));

            Optional<CategoryResponse> categoryResponse = categoryService.update(id, categoryRequest);

            assertTrue(categoryResponse.isPresent());
            assertEquals(categoryResponse.get().getName(), name);
        }

        @Test
        void CategoryService_ShouldNotUpdate_WhenExists() {
            CategoryRequest categoryRequest = CategoryHelperFactory.makeCategoryRequest();
            Category category = CategoryHelperFactory.defaultCategory();
            String name = categoryRequest.getName();
            Long id = category.getId();
            when(categoryRepository.existsByNameAndIdNot(name, category.getId())).thenReturn(true);

            Optional<CategoryResponse> optionalCategoryResponse = categoryService.update(id, categoryRequest);

            assertTrue(optionalCategoryResponse.isEmpty());
        }

    }

    @Nested
    class GetResponseById {

        @Test
        void CategoryService_ShouldReturnResponseWhenIdExists() {
            Category category = CategoryHelperFactory.defaultCategory();
            when(categoryRepository.findById(category.getId())).thenReturn(Optional.of(category));

            Optional<CategoryResponse> categoryResponse = categoryService.getResponseById(category.getId());

            assertTrue(categoryResponse.isPresent());
        }

        @Test
        void CategoryService_ShouldBeEmptyWhenIdNotExists() {
            Long justId = 99L;
            when(categoryRepository.findById(justId)).thenReturn(Optional.empty());

            Optional<CategoryResponse> optionalCategoryResponse = categoryService.getResponseById(justId);

            assertTrue(optionalCategoryResponse.isEmpty());
            verify(categoryRepository, times(1)).findById(justId);
        }

    }

    @Nested
    class ConvertEntityToDto {

        @Test
        void convertEntityToDto() {
            Category category = CategoryHelperFactory.defaultCategory();

            CategoryResponse categoryResponse = CategoryService.convertEntityToDto(category);

            assertNotNull(categoryResponse.getName());
            assertEquals(categoryResponse.getId(), category.getId());
            assertEquals(categoryResponse.getName(), category.getName());
        }

    }

}