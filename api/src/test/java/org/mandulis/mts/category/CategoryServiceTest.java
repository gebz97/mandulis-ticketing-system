package org.mandulis.mts.category;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

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
        }

        @Test
        void CategoryService_ShouldNotSaveWhenNameExists() {

        }

    }

    @Nested
    class DeleteById {

        @Test
        void CategoryService_DeleteByIdWhenIdExists_ShouldThrowNotFoundException() {
        }

        @Test
        void CategoryService_DeleteByIdWhenExists_ShouldReturnDeletedEntity() {

        }

    }

    @Nested
    class Update {

        @Test
        void CategoryService_ShouldUpdate_WhenNameNotExists() {
        }

        @Test
        void CategoryService_ShouldNotUpdate_WhenExists() {

        }

        @Test
        void CategoryService_ShouldUpdate_WhenSameNameAndId() {

        }

    }

    @Nested
    class GetResponseById {

        @Test
        void getResponseById() {
        }

    }

    @Nested
    class ConvertEntityToDto {

        @Test
        void convertEntityToDto() {

        }

    }

}