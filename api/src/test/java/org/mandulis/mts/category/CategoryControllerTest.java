package org.mandulis.mts.category;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CategoryRepository categoryRepository;

    @Nested
    class GetAllCategories {

        @Test
        public void CategoryController_GetAll_ReturnsEmptyList() throws Exception {
            mockMvc.perform(get("/api/v1/public/categories"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data", hasSize(0)));
        }

        @Test
        @Transactional
        public void CategoryController_GetAll_ReturnsActualValues() throws Exception {
            List<Category> categories = CategoryHelperFactory.listOfCategories();
            categoryRepository.saveAll(categories);

            mockMvc.perform(get("/api/v1/public/categories"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data", hasSize(2)))
                    .andExpect(jsonPath("$.data[0].name").value("Music"))
                    .andExpect(jsonPath("$.data[1].name").value("Comedian"));
        }

    }

    @Nested
    class GetCategoryById {

        @Test
        @Transactional
        public void CategoryController_GetCategoryById_ReturnsActualValue() throws Exception {
            Category category = CategoryHelperFactory.defaultCategory();
            Category savedCategory = categoryRepository.save(category);

            mockMvc.perform(get("/api/v1/public/categories/" + savedCategory.getId()))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.['data'].id").value(savedCategory.getId()))
                    .andExpect(jsonPath("$.['data'].name").value(savedCategory.getName()))
                    .andExpect(jsonPath("$.['data'].description").value(savedCategory.getDescription()));
        }

        @Test
        public void CategoryController_GetCategoryById_ReturnsNothing() throws Exception {
            mockMvc.perform(get("/api/v1/public/categories/99"))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.['data']").value(nullValue()))
                    .andExpect(jsonPath("$.['message']").value("Category not found"))
                    .andExpect(jsonPath("$.['success']").value(false));
        }

    }

    @Nested
    class CreateCategory {

        @Test
        @Transactional
        public void CategoryController_ShouldCreateCategoryIfNotExists() throws Exception {
            CategoryRequest categoryRequest = CategoryHelperFactory.makeCategoryRequest();
            MockHttpServletRequestBuilder request =
                    MockMvcRequestBuilders.post("/api/v1/public/categories")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(categoryRequest));

            mockMvc.perform(request)
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.['message']")
                            .value("Category created successfully"))
                    .andExpect(jsonPath("$.['data'].id").value(notNullValue()))
                    .andExpect(jsonPath("$.['data'].name").value(categoryRequest.getName()))
                    .andExpect(jsonPath("$.['data'].description").value(categoryRequest.getDescription()));
        }

        @Test
        @Transactional
        public void CategoryController_ShouldNotCreateCategoryIfNameExists() throws Exception {
            Category category = CategoryHelperFactory.defaultCategory();
            CategoryRequest categoryRequest = CategoryHelperFactory.makeCategoryRequest();
            categoryRepository.save(category);

            MockHttpServletRequestBuilder request = MockMvcRequestBuilders.
                    post("/api/v1/public/categories")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(categoryRequest));

            mockMvc.perform(request)
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.['data']").value(nullValue()))
                    .andExpect(jsonPath("$.['message']").
                            value("Category with this name already exists"));

        }

    }

    @Nested
    class UpdateCategory {

        @Test
        @Transactional
        public void CategoryController_ShouldUpdateIfNameNotExists() throws Exception {
            CategoryRequest categoryRequest = CategoryHelperFactory.makeCategoryRequestForUpdate();
            Category savedCategory = categoryRepository.save(CategoryHelperFactory.defaultCategory());

            MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                    .put("/api/v1/public/categories/" + savedCategory.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(categoryRequest));

            mockMvc.perform(request)
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.['data'].id").value(savedCategory.getId()))
                    .andExpect(jsonPath("$.['data'].name").value(categoryRequest.getName()))
                    .andExpect(jsonPath("$.['data'].description").value(categoryRequest.getDescription()))
                    .andExpect(jsonPath("$.['message']").value("Category updated successfully"));
        }

        @Test
        @Transactional
        public void CategoryController_ShouldNotUpdateIfNameExists() throws Exception {
            CategoryRequest categoryRequest = CategoryHelperFactory.makeCategoryRequest();
            categoryRepository.save(CategoryHelperFactory.defaultCategory());

            MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                    .put("/api/v1/public/categories/" + 99)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(categoryRequest));

            mockMvc.perform(request)
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.['data']").value(nullValue()))
                    .andExpect(jsonPath("$.['message']").value("Category not found"));
        }

    }

    @Nested
    class DeleteCategory {

        @Test
        @Transactional
        public void CategoryController_ShouldDeleteCategoryIfIdExists() throws Exception {
            Category savedCategory = categoryRepository.save(CategoryHelperFactory.defaultCategory());

            mockMvc.perform(delete("/api/v1/public/categories/" + savedCategory.getId()))
                    .andExpect(status().isNoContent())
                    .andExpect(jsonPath("$.['data']").value(nullValue()))
                    .andExpect(jsonPath("$.['message']").value("Category deleted successfully"));
        }

        @Test
        public void CategoryController_ShouldThrowExceptionIfNotExists() throws Exception {
            mockMvc.perform(delete("/api/v1/public/categories/" + 100))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.content[0].error").value("Category not found"));
        }

    }
}