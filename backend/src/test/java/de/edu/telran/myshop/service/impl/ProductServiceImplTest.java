package de.edu.telran.myshop.service.impl;

import de.edu.telran.myshop.dto.CreateProductDto;
import de.edu.telran.myshop.entity.Product;
import de.edu.telran.myshop.entity.ProductCategory;
import de.edu.telran.myshop.mapper.ProductMapper;
import de.edu.telran.myshop.mapper.UpdateProductMapper;
import de.edu.telran.myshop.repository.ProductRepository;

import liquibase.pro.packaged.B;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.scheduling.config.Task;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    ProductRepository productRepository;
    @Mock
    ProductMapper productMapper;
    @Mock
    UpdateProductMapper updateProductMapper;
    @Mock
    EntityManager entityManager;

    @InjectMocks
    ProductServiceImpl productService;

    private static List<Product> initProducts = new ArrayList<>();

    @BeforeAll
    static void initData() {
        var category1 = new ProductCategory("Category 1", 0);
        initProducts = List.of(
                Product.builder().
                        name("product 1").
                        price(new BigDecimal(100))
                        .wholesalePrice(new BigDecimal(50)).
                        quantity(2).
                        category(category1).build(),
                Product.builder().
                        name("product 2").
                        price(new BigDecimal(10.2))
                        .wholesalePrice(new BigDecimal(5.06)).
                        quantity(2000).
                        category(category1).build()
        );
    }

    @Test
    @DisplayName("return list of all products with flag isActive=true")
    void getAllProducts_ReturnsListProduct() {
        // given

        when(this.productRepository.findByActiveTrue()).thenReturn(initProducts);

        // when
        var result = this.productService.getAllProducts();

        // then
        assertEquals(result, initProducts);

    }

    @Test
    @DisplayName("create new Product")
    void createProduct_ReturnsProduct() {
        // Arrange
        when(productMapper.toEntity(any(CreateProductDto.class))).thenReturn(initProducts.get(0));
        CreateProductDto createProductDto = new CreateProductDto("product1", new BigDecimal(10),
                new BigDecimal(5), 2, new ProductCategory("cat 1", 0));
        Product newProduct = Product.builder().
                name("product 2").
                price(new BigDecimal(10.2))
                .wholesalePrice(new BigDecimal(5.06)).
                quantity(2000).
                category(new ProductCategory("category1", 0)).build();
        when(productRepository.saveAndFlush(any(Product.class))).thenReturn(newProduct);


        // Act
        var result = this.productService.createProduct(createProductDto);


        // Assert
        // verify if created product is equal to expected
        assertEquals(result, newProduct);
        // verify if mapper was invoked with correct argument
        verify(productMapper).toEntity(createProductDto);
        // verify if saveAndFlush was invoked with correct arguments
        verify(productRepository).saveAndFlush(any(Product.class));
    }

    @Test
    void createProduct_ShouldBeTransactional() throws NoSuchMethodException {
        // Arrange
        // Act
        boolean isTransactional = ProductServiceImpl.class.getMethod("createProduct", CreateProductDto.class)
                .isAnnotationPresent(Transactional.class);

        // Assert
        assertEquals(true, isTransactional); // Проверяем, что метод помечен как @Transactional
    }

}