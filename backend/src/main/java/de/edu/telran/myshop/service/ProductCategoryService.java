
package de.edu.telran.myshop.service;

import de.edu.telran.myshop.entity.ProductCategory;

import java.util.List;

public interface ProductCategoryService {
    List<ProductCategory> getAllCategories();

    ProductCategory getById(Integer categoryId);

    ProductCategory createProductCategory(final ProductCategory productCategory);

    ProductCategory update(final ProductCategory productCategory);

    void delete(final Integer categoryId);

}
