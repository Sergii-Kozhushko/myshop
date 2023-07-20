/**
 * ProductServiceInterface.java
 *
 * @author Sergii Kozhushko, sergiikozhushko@gmail.com
 * Date of creation: 08-Jul-2023 12:33
 */

package de.edu.telran.myshop.service.interfaces;

import de.edu.telran.myshop.entity.ProductCategory;

import java.util.List;

public interface ProductCategoryService {
   List<ProductCategory> getAllCategories();
   ProductCategory createProductCategory(final ProductCategory productCategory);
   ProductCategory update(final ProductCategory productCategory);
   void delete(final Integer categoryId);

}
