package de.edu.telran.myshop.service.impl;

import de.edu.telran.myshop.dto.CreateProductDto;
import de.edu.telran.myshop.entity.Product;
import de.edu.telran.myshop.entity.ProductCategory;
import de.edu.telran.myshop.mapper.ProductMapper;
import de.edu.telran.myshop.mapper.UpdateProductMapper;
import de.edu.telran.myshop.repository.ProductCategoryRepository;
import de.edu.telran.myshop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductCategoryServiceImplTest {
    @Mock
    ProductCategoryRepository categoryRepository;

    @Mock
    EntityManager entityManager;

    @InjectMocks
    ProductCategoryServiceImpl categoryService;
    //
    private static List<ProductCategory> initCategories = new ArrayList<>();

    @BeforeAll
    static void initData() {

        initCategories = List.of(
                ProductCategory.builder().
                        name("product category 1")
                        .active(true)
                        .parentId(0)
                        .build(),
                ProductCategory.builder().
                        name("product category 2")
                        .active(true)
                        .parentId(0)
                        .build(),
                ProductCategory.builder().
                        name("product category 3")
                        .active(false)
                        .parentId(0)
                        .build()
        );
    }


    @Test
    @DisplayName("return list of all categories with flag isActive=true")
    void getAllCategories_ReturnsListCategories() {
        // given

        when(this.categoryRepository.findByActiveTrueOrderByNameAsc()).thenReturn(initCategories);

        // when
        var result = this.categoryService.getAllCategories();

        // then
        assertEquals(result, initCategories);
    }

    @Test
    void getById_ValidId_ReturnsCategory() {
        // given
        int categoryId = 1;
        ProductCategory category = initCategories.get(0);
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        // when
        ProductCategory result = categoryService.getById(categoryId);
        // then
        assertEquals(result, category);
    }

    @Test
    void getById_InvalidId_ReturnsNull() {
        int invalidCategoryId = 100;
        when(categoryRepository.findById(invalidCategoryId)).thenReturn(Optional.empty());
        ProductCategory result = categoryService.getById(invalidCategoryId);
        assertNull(result);
    }

    @Test
    void createProductCategory_ValidCategory_ReturnsCreatedCategory() {
        ProductCategory newCategory = initCategories.get(0);
        when(categoryRepository.saveAndFlush(newCategory)).thenReturn(newCategory);
        ProductCategory result = categoryService.createProductCategory(newCategory);
        assertEquals(result, newCategory);
    }

    @Test
    public void testDeleteExistingCategory() {
        int categoryId = 1;
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(new ProductCategory()));

        categoryService.delete(categoryId);

        verify(categoryRepository, times(1)).deleteById(categoryId);
    }


}