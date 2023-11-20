package de.edu.telran.myshop.controller;

import de.edu.telran.myshop.config.UriLinks;
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
@RequestMapping(UriLinks.SUPPLIER_URI)
@RequiredArgsConstructor
public class SupplierController {

    private final SupplierServiceImpl supplierService;

    @GetMapping("/all")
    public ResponseEntity<List<Supplier>> getAllSuppliers() {

        return ResponseEntity.ok(supplierService.getAll());
    }

    @Transactional
    @PostMapping("/add")
    public ResponseEntity<Supplier> createSupplier(@RequestBody final Supplier supplier)
            throws URISyntaxException {
        return ResponseEntity
                .created(new URI(UriLinks.SUPPLIER_URI))
                .body(supplierService.create(supplier));
    }

    @Transactional
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteSupplier(@PathVariable final Long id) {
        supplierService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Supplier> getSupplierById(@PathVariable("id") Long id
    ) {
        return ResponseEntity.ok(supplierService.getById(id));
    }

    @PutMapping("/update")
    public ResponseEntity<Supplier> updateSupplier(@RequestBody final Supplier supplier
    ) throws URISyntaxException {

        return ResponseEntity.created(new URI(UriLinks.SUPPLIER_URI))
                .body(supplierService.update(supplier));
    }

    @GetMapping("/max-id/")
    public ResponseEntity<Long> getSupplierMaxId() {
        return ResponseEntity.ok(supplierService.findMaxId());
    }
}