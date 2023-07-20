/**
 * ProductServiceInterface.java
 *
 * @author Sergii Kozhushko, sergiikozhushko@gmail.com
 * Date of creation: 08-Jul-2023 12:33
 */

package de.edu.telran.myshop.service.interfaces;

import de.edu.telran.myshop.dto.CreateProductDto;
import de.edu.telran.myshop.entity.Product;

import java.util.List;

public interface ProductService {
   public Product createProduct(final CreateProductDto product);
   void deleteProduct(final Long productId);
   public List<Product> getAllProducts();
   public Product getProductById(final Long productId);

   public Product updateProduct(final Product product);

}
