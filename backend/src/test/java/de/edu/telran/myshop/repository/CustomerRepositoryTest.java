package de.edu.telran.myshop.repository;

import de.edu.telran.myshop.entity.Customer;
import de.edu.telran.myshop.entity.Product;
import de.edu.telran.myshop.entity.ProductCategory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import javax.persistence.EntityManager;
import javax.validation.ConstraintViolationException;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


//@DataJpaTest // make app-context only with jpa-layer
// don't replace actual bd with test h2
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@SpringBootTest
//class CustomerRepositoryTest {
//    @Autowired
//    private CustomerRepository customerRepository;
//    @Autowired
//    private EntityManager entityManager;
//
//    @Test
//    void saveCustomer_checkSaved() {
//        Customer customerToSave = Customer.builder().
//                name("John Travolta")
//                .email("john1@gmail.com")
//                .address("121 Amazon St, Seattle, WA")
//                .phone("+1 555-123-4667")
//                .acceptSMSList(true)
//                .build();
//        customerRepository.save(customerToSave);
//        Customer result = customerRepository.findById(customerToSave.getId()).orElse(null);
//        assertNotNull(result);
//        assertEquals(result.getId(), customerToSave.getId());
//        assertEquals(result.getName(), customerToSave.getName());
//    }
//
//    @Test
//    void saveCustomer_invalidName_throwsConstraintViolationException() {
//        Customer customer = new Customer();
//        customer.setName("1234567891234567891234567891"); // Name longer than 30 characters
//        customer.setEmail("john@example.com");
//        customer.setAddress("123 Main St");
//        customer.setPhone("+1 555-123-4567");
//        customer.setAcceptSMSList(true);
//
//        assertThrows(ConstraintViolationException.class, () -> {
//            customerRepository.save(customer);
//        });
//    }


//
//    // add and find at least one record with is_Active=true
//    @Test
//    void findByActiveTrue() {
//        ProductCategory category1 = categoryRepository.save(new ProductCategory("Category 1"));
//
//        Product productToSave = Product.builder().
//                name("product 1")
//                .price(new BigDecimal("10.2"))
//                .wholesalePrice(new BigDecimal("5.07"))
//                .quantity(2000)
//                .active(true)
//                .category(category1)
//                .build();
//
//        customerRepository.saveAndFlush(productToSave);
//        List<Product> result = customerRepository.findByActiveTrue();
//        assertTrue(result.size() != 0);
//    }
//
//    @Test
//    void findById_existingId_productReturned() {
//        // Save a test product to the database
//        ProductCategory category1 = categoryRepository.save(new ProductCategory("Category 1"));
//        Product productToSave = Product.builder().
//                name("product 1")
//                .price(new BigDecimal("10.2"))
//                .wholesalePrice(new BigDecimal("5.07"))
//                .quantity(2000)
//                .active(true)
//                .category(category1)
//                .build();
//
//        Product savedProduct = customerRepository.save(productToSave);
//        Long savedProductId = savedProduct.getId();
//
//        // Search for the product by the saved ID
//        Product foundProduct = customerRepository.findById(savedProductId).orElse(null);
//
//        // Check that the found product is not null and matches the saved product
//        assertNotNull(foundProduct);
//        assertEquals(savedProductId, foundProduct.getId());
//        assertEquals(productToSave.getName(), foundProduct.getName());
//        assertEquals(productToSave.getPrice(), foundProduct.getPrice());
//    }
//
//    @Test
//    void findByCategoryIdAndActiveTrue_categoryIdProductReturned() {
//        ProductCategory category1 = categoryRepository.save(new ProductCategory("category 1"));
//        Product productToSave = Product.builder().
//                name("product 1")
//                .price(new BigDecimal("10.2"))
//                .wholesalePrice(new BigDecimal("5.07"))
//                .quantity(2000)
//                .active(true)
//                .category(category1)
//                .build();
//        customerRepository.save(productToSave);
//
//        List<Product> result = customerRepository.findByCategoryIdAndActiveTrue(category1.getId());
//        assertEquals(1, result.size());
//        assertTrue(result.contains(productToSave));
//        assertTrue(result.get(0).getActive());
//    }
//
//    @Test
//    @DirtiesContext
//    void deleteById_existingId_productDeleted() {
//        // Find one of the saved products by its ID
//        ProductCategory category1 = categoryRepository.save(new ProductCategory("category 1"));
//        Product productToDelete = Product.builder().
//                name("product 1")
//                .price(new BigDecimal("10.2"))
//                .wholesalePrice(new BigDecimal("5.07"))
//                .quantity(2000)
//                .active(true)
//                .category(category1)
//                .build();
//        //entityManager.flush();
//        customerRepository.save(productToDelete);
//        Long productIdToDelete = productToDelete.getId();
//        productToDelete = customerRepository.findById(productIdToDelete).orElse(null);
//        assertNotNull(productToDelete);
//
//        // Delete the product by its ID
//        customerRepository.deleteById(productIdToDelete);
//
//        // Try to find the product by its ID after deletion
//        Product deletedProduct = customerRepository.findById(productIdToDelete).orElse(null);
//
//        // Assert that the deletedProduct is null (productToDelete is deleted)
//        assertNull(deletedProduct);
//    }
//}