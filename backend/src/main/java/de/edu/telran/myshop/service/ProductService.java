package de.edu.telran.myshop.service;

import de.edu.telran.myshop.dto.CreateProductDto;
import de.edu.telran.myshop.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface ProductService {

    Product createProduct(final CreateProductDto product);

    void deleteProduct(final Long productId);

    List<Product> getAllProducts();

    Product getProductById(final Long productId);

    Product updateProduct(final Product product);

    Page<Product> findByParams(String name, Boolean active, PageRequest paging);

}