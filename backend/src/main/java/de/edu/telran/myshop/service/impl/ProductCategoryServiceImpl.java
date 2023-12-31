package de.edu.telran.myshop.service.impl;

import de.edu.telran.myshop.entity.ProductCategory;
import de.edu.telran.myshop.exception.ErrorMassage;
import de.edu.telran.myshop.exception.ProductCategoryInvalidParameterException;
import de.edu.telran.myshop.exception.ProductCategoryNotFoundException;
import de.edu.telran.myshop.repository.ProductCategoryRepository;
import de.edu.telran.myshop.service.ProductCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductCategoryServiceImpl implements ProductCategoryService {

    private final ProductCategoryRepository categoryRepository;

    @Override
    public List<ProductCategory> getAllCategories() {
        return categoryRepository.findByActiveTrueOrderByNameAsc();
    }

    public ProductCategory getById(Integer categoryId) {
        if (categoryId == null) {
            return new ProductCategory();
        }
        return categoryRepository.findById(categoryId).orElse(null);
    }


    @Override
    @Transactional
    public ProductCategory createProductCategory(final ProductCategory productCategory) {
        if (productCategory.getId() != null && productCategory.getId() != 0) {
            throw new ProductCategoryInvalidParameterException(ErrorMassage.CATEGORY_ADD_WITH_ID_ERROR);
        }
        if (productCategory.getName() == null || productCategory.getName().isBlank()) {
            throw new ProductCategoryInvalidParameterException(ErrorMassage.CATEGORY_NAME_EMPTY);
        }
        productCategory.setName(productCategory.getName().trim());
        return categoryRepository.saveAndFlush(productCategory);
    }

    @Override
    public ProductCategory update(final ProductCategory productCategory) {
        if (productCategory.getId() == null || productCategory.getId() == 0) {
            throw new ProductCategoryInvalidParameterException(ErrorMassage.CATEGORY_ID_EMPTY);
        }
        // check if Category exists
        categoryRepository.findById(productCategory.getId()).orElseThrow(() ->
                new ProductCategoryNotFoundException(ErrorMassage.CATEGORY_NOT_FOUND
                        + ". Id = " + productCategory.getId()));

        if (productCategory.getName() == null || productCategory.getName().isBlank()) {
            throw new ProductCategoryInvalidParameterException(ErrorMassage.CATEGORY_NAME_EMPTY);
        }
        return categoryRepository.saveAndFlush(productCategory);
    }

    @Override
    @Transactional
    public void delete(final Integer categoryId) {
        if (categoryRepository.findById(categoryId).isEmpty()) {
            throw new ProductCategoryNotFoundException(ErrorMassage.CATEGORY_NOT_FOUND);
        } else {
            categoryRepository.deleteById(categoryId);
        }
    }

}