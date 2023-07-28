/**
 * ProductService.java
 *
 * @author Sergii Kozhushko, sergiikozhushko@gmail.com
 * Date of creation: 27-Jun-2023 18:46
 */

package de.edu.telran.myshop.service.impl;

import de.edu.telran.myshop.dto.CreateProductDto;
import de.edu.telran.myshop.entity.Product;
import de.edu.telran.myshop.exception.*;
import de.edu.telran.myshop.mapper.ProductMapper;
import de.edu.telran.myshop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import de.edu.telran.myshop.service.interfaces.*;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    @Transactional
    public Product createProduct(final CreateProductDto createProductDto) {
        return productRepository.saveAndFlush(productMapper.toEntity(createProductDto));
    }

    @Override
    public Product updateProduct(final Product product) {
        if (product.getId() == null || product.getId() == 0) {
            throw new InvalidProductParameterException(ErrorMassage.PRODUCT_UPDATE_EMPTY_ID);
        }
        // check if Product exists
        productRepository.findById(product.getId()).orElseThrow(() ->
                new ProductNotFoundException(ErrorMassage.PRODUCT_ID_NOT_FOUND));

        if (product.getName() == null) {
            throw new ProductNameEmptyException(ErrorMassage.PRODUCT_UPDATE_ERROR);
        }
        return productRepository.saveAndFlush(product);
    }

    @Override
    public Product getProductById(final Long productId) {
        return productRepository
                .findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(ErrorMassage.PRODUCT_ID_NOT_FOUND));
    }

    public List<Product> getProductsByCategory(final Integer categoryId) {
        return productRepository.findByCategory(categoryId);
    }

    @Override
    @Transactional
    public void deleteProduct(final Long productId) {

        if (!productRepository.findById(productId).isPresent()) {
            throw new ProductNotFoundException(ErrorMassage.PRODUCT_ID_NOT_FOUND);
        } else {
            productRepository.deleteById(productId);
        }
    }


    @Cacheable(cacheNames = "products")
    public Page<Product> findByParams(String name, Boolean active, PageRequest paging) {
        return productRepository.findByParams(name, active, paging);
    }

}
