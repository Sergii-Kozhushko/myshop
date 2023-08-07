/**
 * ProductController.java
 *
 * @author Sergii Kozhushko, sergiikozhushko@gmail.com
 * Date of creation: 27-Jun-2023 18:48
 */

package de.edu.telran.myshop.controller.rest;

import de.edu.telran.myshop.entity.Supplier;
import de.edu.telran.myshop.service.impl.SupplierServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping(SupplierController.MAIN_PATH)
@RequiredArgsConstructor
public class SupplierController {
    private final SupplierServiceImpl supplierService;
    public static final String MAIN_PATH = "/supplier";
    public static final String ID_COLUMN = "id";

    @GetMapping("/all")
    public ResponseEntity<List<Supplier>> getAllSuppliers() {

        return ResponseEntity.ok(supplierService.getAll());
    }

//    @PostMapping("/search")
//    public ResponseEntity<Page<Supplier>> search(@RequestBody SupplierSearchValues supplierSearchValues) throws ParseException {
//
//        // preparing data for page view
//        String sortDirection = supplierSearchValues.getSortDirection();
//        Sort.Direction direction = sortDirection == null || sortDirection.trim().length() == 0 ||
//                sortDirection.trim().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
//
//        Sort sort = Sort.by(direction, supplierSearchValues.getSortColumn(), ID_COLUMN);
//        Integer pageNumber = supplierSearchValues.getPageNumber() != null ? supplierSearchValues.getPageNumber() : 0;
//        PageRequest pageRequest = PageRequest.of(supplierSearchValues.getPageNumber(), supplierSearchValues.getPageSize(), sort);
//        Page<Supplier> result = supplierService.findByParams(
//                supplierSearchValues.getName(),
//                supplierSearchValues.getEmail(),
//                supplierSearchValues.getPhone(), pageRequest);
//
//        return ResponseEntity.ok(result);
//    }


    @Transactional
    @PostMapping("/add")
    public ResponseEntity<Supplier> createSupplier(@RequestBody final Supplier supplier)
            throws URISyntaxException {
        return ResponseEntity
                .created(new URI(MAIN_PATH))
                .body(supplierService.create(supplier));
    }

    @Transactional
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteSupplier(@PathVariable final Long id) throws Exception {
        supplierService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Supplier> getSupplierById(@PathVariable("id") Long id
    ) throws Exception {
        return ResponseEntity.ok(supplierService.getById(id));
    }

    @PutMapping("/update")
    public ResponseEntity<Supplier> updateSupplier(@RequestBody final Supplier supplier
    ) throws Exception {

        return ResponseEntity.created(new URI(MAIN_PATH))
                .body(supplierService.update(supplier));
    }

    @GetMapping("/max-id/")
    public ResponseEntity<Long> getSupplierMaxId() {
        return ResponseEntity.ok(supplierService.findMaxId());
    }

//    @PutMapping("/mark-as-deleted")
//    public ResponseEntity<Supplier> markSupplierAsDeleted(@RequestBody final Supplier supplier
//    ) throws Exception {
//        supplier.setActive(false);
//        return ResponseEntity.created(new URI(MAIN_PATH))
//                .body(supplierService.update(supplier));
//    }


}
