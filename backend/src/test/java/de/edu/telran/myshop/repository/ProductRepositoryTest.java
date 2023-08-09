package de.edu.telran.myshop.repository;

import de.edu.telran.myshop.entity.Product;
import de.edu.telran.myshop.entity.ProductCategory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@Transactional // roll-back after test execution
//@TestPropertySource(properties = {
//        "spring.jpa.hibernate.ddl-auto=validate"
//})

class ProductRepositoryTest {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductCategoryRepository categoryRepository;


    @Autowired
    private TestEntityManager entityManager;


    @Test
    void saveProduct_checkSaved() {
        Product productToSave = Product.builder().
                name("product 3")
                .price(new BigDecimal("10.2"))
                .wholesalePrice(new BigDecimal("5.07"))
                .quantity(2000)
                .active(true)
                .category(new ProductCategory("Category 1"))
                .build();
        productRepository.save(productToSave);
        Product result = productRepository.findById(productToSave.getId()).orElse(null);
        assertNotNull(result);
        assertEquals(result.getId(), productToSave.getId());
        assertEquals(result.getName(), productToSave.getName());
    }

    // add and find at least one record with is_Active=true
    @Test
    void findByActiveTrue() {
        ProductCategory category1 = categoryRepository.save(new ProductCategory("Category 1"));

        Product productToSave = Product.builder().
                name("product 1")
                .price(new BigDecimal("10.2"))
                .wholesalePrice(new BigDecimal("5.07"))
                .quantity(2000)
                .active(true)
                .category(category1)
                .build();

        productRepository.saveAndFlush(productToSave);
        List<Product> result = productRepository.findByActiveTrue();
        assertTrue(result.size() != 0);
    }

    @Test
    void findById_existingId_productReturned() {
        // Save a test product to the database
        ProductCategory category1 = categoryRepository.save(new ProductCategory("Category 1"));
        Product productToSave = Product.builder().
                name("product 1")
                .price(new BigDecimal("10.2"))
                .wholesalePrice(new BigDecimal("5.07"))
                .quantity(2000)
                .active(true)
                .category(category1)
                .build();

        Product savedProduct = productRepository.save(productToSave);
        Long savedProductId = savedProduct.getId();

        // Search for the product by the saved ID
        Product foundProduct = productRepository.findById(savedProductId).orElse(null);

        // Check that the found product is not null and matches the saved product
        assertNotNull(foundProduct);
        assertEquals(savedProductId, foundProduct.getId());
        assertEquals(productToSave.getName(), foundProduct.getName());
        assertEquals(productToSave.getPrice(), foundProduct.getPrice());
    }

    @Test
    void findByCategoryIdAndActiveTrue_categoryIdProductReturned() {
        ProductCategory category1 = categoryRepository.save(new ProductCategory("category 1"));
        Product productToSave = Product.builder().
                name("product 1")
                .price(new BigDecimal("10.2"))
                .wholesalePrice(new BigDecimal("5.07"))
                .quantity(2000)
                .active(true)
                .category(category1)
                .build();
        productRepository.save(productToSave);

        List<Product> result = productRepository.findByCategoryIdAndActiveTrue(category1.getId());
        assertEquals(1, result.size());
        assertTrue(result.contains(productToSave));
        assertTrue(result.get(0).getActive());
    }

    @Test
    @DirtiesContext
    void deleteById_existingId_productDeleted() {
        // Find one of the saved products by its ID
        ProductCategory category1 = categoryRepository.save(new ProductCategory("category 1"));
        Product productToDelete = Product.builder().
                name("product 1")
                .price(new BigDecimal("10.2"))
                .wholesalePrice(new BigDecimal("5.07"))
                .quantity(2000)
                .active(true)
                .category(category1)
                .build();
        //entityManager.flush();
        productRepository.save(productToDelete);
        Long productIdToDelete = productToDelete.getId();
        productToDelete = productRepository.findById(productIdToDelete).orElse(null);
        assertNotNull(productToDelete);

        // Delete the product by its ID
        productRepository.deleteById(productIdToDelete);

        // Try to find the product by its ID after deletion
        Product deletedProduct = productRepository.findById(productIdToDelete).orElse(null);

        // Assert that the deletedProduct is null (productToDelete is deleted)
        assertNull(deletedProduct);
    }
}