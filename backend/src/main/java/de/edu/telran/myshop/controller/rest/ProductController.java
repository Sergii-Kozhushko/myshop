/**
 * ProductController.java
 *
 * @author Sergii Kozhushko, sergiikozhushko@gmail.com
 * Date of creation: 27-Jun-2023 18:48
 */

package de.edu.telran.myshop.controller.rest;

import de.edu.telran.myshop.dto.CreateProductDto;
import de.edu.telran.myshop.dto.TestDataResultDto;
import de.edu.telran.myshop.entity.Product;
import de.edu.telran.myshop.search.ProductSearchValues;
import de.edu.telran.myshop.service.impl.ProductServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping(ProductController.MAIN_PATH)
@RequiredArgsConstructor
public class ProductController {
    private final ProductServiceImpl productService;
    public static final String MAIN_PATH = "/product";
    public static final String ID_COLUMN = "id";


    @GetMapping("/test")
    public ResponseEntity<TestDataResultDto> getTest() {

        return ResponseEntity.ok(new TestDataResultDto("test data from beckend"));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Product>> getAllProducts() {

        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/getbycategory/{categoryId}")
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

        Sort sort = Sort.by(direction, productSearchValues.getSortColumn(), ID_COLUMN);
        Integer pageNumber = productSearchValues.getPageNumber() != null ? productSearchValues.getPageNumber() : 0;
        PageRequest pageRequest = PageRequest.of(productSearchValues.getPageNumber(), productSearchValues.getPageSize(), sort);
        Page<Product> result = productService.findByParams(productSearchValues.getName(), active, pageRequest);


        return ResponseEntity.ok(result);
    }

    @PostMapping("/add")
    public ResponseEntity<Product> createProduct(@RequestBody final CreateProductDto product)
            throws URISyntaxException {
        return ResponseEntity
                .created(new URI(MAIN_PATH))
                .body(productService.createProduct(product));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable final Long id) throws Exception {

        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") Long id
    ) throws Exception {

        return ResponseEntity.ok(productService.getProductById(id));
    }

    @PutMapping("/update")
    public ResponseEntity<Product> updateProduct(@RequestBody final Product product
    ) throws Exception {

        return ResponseEntity.ok(productService.updateProduct(product));
    }

}
