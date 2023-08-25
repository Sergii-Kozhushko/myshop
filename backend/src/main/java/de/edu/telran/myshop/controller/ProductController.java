package de.edu.telran.myshop.controller;

import de.edu.telran.myshop.config.URILinks;
import de.edu.telran.myshop.dto.CreateProductDto;
import de.edu.telran.myshop.entity.Product;
import de.edu.telran.myshop.search.ProductSearchValues;
import de.edu.telran.myshop.service.impl.ProductServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping(URILinks.PRODUCT_URI)
@RequiredArgsConstructor
public class ProductController {
    private final ProductServiceImpl productService;


    @GetMapping("/all")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> result = productService.getAllProducts();

        return ResponseEntity.ok(result);
        //return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/bycategory/{categoryId}")
    public ResponseEntity<List<Product>> getProductByCategoryId(@PathVariable("categoryId") Integer categoryId
    ) throws Exception {

        return ResponseEntity.ok(productService.getProductsByCategory(categoryId));
    }

    @PostMapping("/search")
    public ResponseEntity<Page<Product>> search(@RequestBody ProductSearchValues productSearchValues) throws ParseException {

        Boolean active = productSearchValues.getActive();
        // preparing data for page view
        String sortDirection = productSearchValues.getSortDirection();
        Sort.Direction direction = sortDirection == null || sortDirection.trim().length() == 0 ||
                sortDirection.trim().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;

        Sort sort = Sort.by(direction, productSearchValues.getSortColumn(), URILinks.ID_COLUMN);
        Integer pageNumber = productSearchValues.getPageNumber() != null ? productSearchValues.getPageNumber() : 0;
        PageRequest pageRequest = PageRequest.of(productSearchValues.getPageNumber(), productSearchValues.getPageSize(), sort);
        Page<Product> result = productService.findByParams(productSearchValues.getName(), active, pageRequest);


        return ResponseEntity.ok(result);
    }

    @PostMapping("/add")
    public ResponseEntity<Product> createProduct(@RequestBody final CreateProductDto product)
            throws URISyntaxException {

        return ResponseEntity
                .created(new URI(URILinks.PRODUCT_URI))
                .body(productService.createProduct(product));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable final Long id) {

        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") Long id
    ) {

        return ResponseEntity.ok(productService.getProductById(id));
    }


    @PutMapping("/update")
    @Transactional
    public ResponseEntity<Product> updateProduct(@RequestBody final Product product
    ) {

        return ResponseEntity.ok(productService.updateProduct(product));
    }


}
