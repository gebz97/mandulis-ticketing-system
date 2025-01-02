package org.mandulis.mts.category;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository testRepository;


    @Test
    void CategoryRepository_Save_ReturnSavedCategory() {
        Category category = CategoryHelperFactory.defaultCategory();

        Category save = testRepository.save(category);

        assertThat(save).isNotNull();
        assertThat(save.getName()).isEqualTo(CategoryHelperFactory.defaultCategory().getName());
        assertThat(save.getDescription()).isEqualTo(CategoryHelperFactory.defaultCategory().getDescription());
    }

    @Test
    void CategoryRepository_FindByName() {
        Category category = CategoryHelperFactory.defaultCategory();

        testRepository.save(category);

        Category savedCategory = testRepository.findByName(CategoryHelperFactory.defaultCategory().getName()).get();

        assertThat(savedCategory).isNotNull();
        assertThat(savedCategory.getName()).isEqualTo(CategoryHelperFactory.defaultCategory().getName());
        assertThat(savedCategory.getId()).isGreaterThan(0);
    }

    @Test
    void CategoryRepository_ExistsByName() {
        Category category1 = CategoryHelperFactory.defaultCategory();
        Category category2 = CategoryHelperFactory.defaultCategorySec();

        Category savedCategory1 = testRepository.save(category1);
        Category savedCategory2 = testRepository.save(category2);

        boolean existsBySameNameButIdNot = testRepository.existsByNameAndIdNot(
                CategoryHelperFactory.defaultCategory().getName(),
                savedCategory2.getId()
        );
        boolean existsByDifferentNameButSameId = testRepository.existsByNameAndIdNot(
                "Comedian",
                savedCategory1.getId()
        );
        boolean existsBySameNameSameId = testRepository.existsByNameAndIdNot(
                CategoryHelperFactory.defaultCategory().getName(),
                savedCategory1.getId()
        );

        assertTrue(existsBySameNameButIdNot);
        assertTrue(existsByDifferentNameButSameId);
        assertFalse(existsBySameNameSameId);
    }

    @Test
    void CategoryRepository_FindAll_ReturnMoreThanOne() {
        Category category1 = CategoryHelperFactory.defaultCategory();
        Category category2 = CategoryHelperFactory.defaultCategorySec();

        testRepository.save(category1);
        testRepository.save(category2);

        List<Category> categories = testRepository.findAll();

        assertThat(categories).isNotEmpty().hasSize(2).extracting(Category::getName).contains(category1.getName());
    }

    @Test
    void CategoryRepository_DeleteById() {
        Category category1 = CategoryHelperFactory.defaultCategory();

        testRepository.save(category1);
        testRepository.deleteById(category1.getId());

        Optional<Category> optionalCategory = testRepository.findById(category1.getId());
        assertTrue(optionalCategory.isEmpty());
    }

}