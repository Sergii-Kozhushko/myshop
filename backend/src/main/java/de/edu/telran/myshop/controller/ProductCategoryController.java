package de.edu.telran.myshop.controller;

import de.edu.telran.myshop.config.UriLinks;
import de.edu.telran.myshop.entity.ProductCategory;
import de.edu.telran.myshop.service.impl.ProductCategoryServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping(UriLinks.CATEGORY_URI)
@RequiredArgsConstructor
public class ProductCategoryController {

    private final ProductCategoryServiceImpl productCategoryService;

    @GetMapping("/all")
    public ResponseEntity<List<ProductCategory>> getAllCategories() {

        return ResponseEntity.ok(productCategoryService.getAllCategories());
    }

    @PostMapping("/add")
    public ResponseEntity<ProductCategory> createCategory(@RequestBody final ProductCategory productCategory)
            throws URISyntaxException {

        return ResponseEntity
                .created(new URI(UriLinks.CATEGORY_URI))
                .body(productCategoryService.createProductCategory(productCategory));
    }

    @PutMapping("/update")
    public ResponseEntity<ProductCategory> updateProductCategory(@RequestBody final ProductCategory productCategory
    ) throws URISyntaxException {

        return ResponseEntity.created(new URI(UriLinks.CATEGORY_URI))
                .body(productCategoryService.update(productCategory));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteProductCategory(@PathVariable final Integer id) {
        productCategoryService.delete(id);
        return ResponseEntity.noContent().build();
    }


}