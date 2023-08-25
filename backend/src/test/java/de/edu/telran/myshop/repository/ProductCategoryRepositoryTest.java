package de.edu.telran.myshop.repository;

import de.edu.telran.myshop.entity.Product;
import de.edu.telran.myshop.entity.ProductCategory;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest // make app-context only with jpa-layer
// don't replace actual bd with test h2
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@Transactional // roll-back after test execution
//@TestPropertySource(properties = {
//        "spring.jpa.hibernate.ddl-auto=validate"
//})


class ProductCategoryRepositoryTest {
    @Autowired
    private ProductCategoryRepository categoryRepository;

    @Test
    void saveProductCategory_checkSaved() {
        ProductCategory categoryToSave = ProductCategory.builder().
                name("product category 1")
                .active(true)
                .build();
        categoryRepository.save(categoryToSave);
        ProductCategory result = categoryRepository.findById(categoryToSave.getId()).orElse(null);
        assertNotNull(result);
        assertEquals(result.getId(), categoryToSave.getId());
        assertEquals(result.getName(), categoryToSave.getName());
    }

    // add and find at least one record with is_Active=true
    @Test
    void findByActiveTrue() {
        ProductCategory category1 = categoryRepository.save(new ProductCategory("Category 1"));

        ProductCategory categoryToSave = ProductCategory.builder().
                name("product category 1")
                .active(true)
                .build();

        List<ProductCategory> resultSet1 = categoryRepository.findByActiveTrue();
        categoryRepository.saveAndFlush(categoryToSave);
        List<ProductCategory> resultSet2 = categoryRepository.findByActiveTrue();
        assertEquals(1, resultSet2.size() - resultSet1.size());


    }

    @Test
    void findById_existingId_productReturned() {
        // Save a test product to the database

        ProductCategory categoryToSave = ProductCategory.builder().
                name("product category 1")
                .active(true)
                .build();

        ProductCategory savedCategory = categoryRepository.save(categoryToSave);
        Integer savedCategoryId = savedCategory.getId();

        // Search for the product by the saved ID
        ProductCategory foundCategory = categoryRepository.findById(savedCategoryId).orElse(null);

        // Check that the found product is not null and matches the saved product
        assertNotNull(foundCategory);
        assertEquals(savedCategoryId, foundCategory.getId());
        assertEquals(categoryToSave.getName(), foundCategory.getName());
    }

    @Test
    @DirtiesContext
    void deleteById_existingId_productDeleted() {
        // Find one of the saved products by its ID

        ProductCategory categoryToDelete = ProductCategory.builder().
                name("product category 1")
                .active(true)
                .build();

        categoryRepository.save(categoryToDelete);
        Integer categoryIdToDelete = categoryToDelete.getId();
        categoryToDelete = categoryRepository.findById(categoryIdToDelete).orElse(null);
        assertNotNull(categoryToDelete);

        // Delete the product category by its ID
        categoryRepository.deleteById(categoryIdToDelete);

        // Try to find the product category by its ID after deletion
        ProductCategory deletedCategory = categoryRepository.findById(categoryIdToDelete).orElse(null);

        // Assert that the deletedCategory is null (categoryToDelete is deleted)
        assertNull(deletedCategory);
    }

    @Test
    void updateProductCategory_checkUpdated() {
        // Save a new product category to the database
        ProductCategory categoryToSave = ProductCategory.builder()
                .name("product category 1")
                .active(true)
                .build();
        categoryRepository.save(categoryToSave);

        // Find the saved product category by its ID
        Integer categoryIdToUpdate = categoryToSave.getId();
        ProductCategory foundCategory = categoryRepository.findById(categoryIdToUpdate).orElse(null);
        assertNotNull(foundCategory);

        // Update the category's properties
        String updatedName = "Updated Category Name";
        boolean updatedActive = false;

        foundCategory.setName(updatedName);
        foundCategory.setActive(updatedActive);
        categoryRepository.save(foundCategory);

        // Retrieve the updated category from the database
        ProductCategory updatedCategory = categoryRepository.findById(categoryIdToUpdate).orElse(null);

        // Check that the updated category's properties match the updated values
        assertNotNull(updatedCategory);
        assertEquals(updatedName, updatedCategory.getName());
        assertEquals(updatedActive, updatedCategory.getActive());
    }
}