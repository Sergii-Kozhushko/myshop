package de.edu.telran.myshop.controller;

import de.edu.telran.myshop.config.URILinks;
import de.edu.telran.myshop.entity.Sale;
import de.edu.telran.myshop.entity.SaleItem;
import de.edu.telran.myshop.service.impl.SaleItemServiceImpl;
import de.edu.telran.myshop.service.impl.SaleServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping(URILinks.SALE_URI)
@RequiredArgsConstructor
public class SaleController {
    private final SaleServiceImpl saleService;
    private final SaleItemServiceImpl saleProductService;


    @GetMapping("/all")
    public ResponseEntity<List<Sale>> getAllSales() {

        return ResponseEntity.ok(saleService.getAll());
    }

    @GetMapping("/all-sort-date-desc")
    public ResponseEntity<List<Sale>> getAllSalesSortDateDesc() {
        return ResponseEntity.ok(saleService.getAllSortByDateDesc());
    }


//    @PostMapping("/search")
//    public ResponseEntity<Page<Customer>> search(@RequestBody CustomerSearchValues customerSearchValues) throws ParseException {
//
//        // preparing data for page view
//        String sortDirection = customerSearchValues.getSortDirection();
//        Sort.Direction direction = sortDirection == null || sortDirection.trim().length() == 0 ||
//                sortDirection.trim().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
//
//        Sort sort = Sort.by(direction, customerSearchValues.getSortColumn(), ID_COLUMN);
//        Integer pageNumber = customerSearchValues.getPageNumber() != null ? customerSearchValues.getPageNumber() : 0;
//        PageRequest pageRequest = PageRequest.of(customerSearchValues.getPageNumber(), customerSearchValues.getPageSize(), sort);
//        Page<Customer> result = customerService.findByParams(
//                customerSearchValues.getName(),
//                customerSearchValues.getEmail(),
//                customerSearchValues.getPhone(), pageRequest);
//
//        return ResponseEntity.ok(result);
//    }

    @PostMapping("/add")
    @Transactional
    public ResponseEntity<Sale> createSale(@RequestBody final Sale sale)
            throws URISyntaxException {

        return ResponseEntity
                .created(new URI(URILinks.SALE_URI))
                .body(saleService.create(sale));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteSale(@PathVariable final Long id) throws Exception {

        saleService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Sale> getSaleById(@PathVariable("id") Long id
    ) throws Exception {

        return ResponseEntity.ok(saleService.getById(id));
    }

    @PutMapping("/update")
    public ResponseEntity<Sale> updateSale(@RequestBody final Sale sale) throws Exception {

        return ResponseEntity.created(new URI(URILinks.SALE_URI))
                .body(saleService.update(sale));
    }

    @GetMapping("/max-id/")
    public ResponseEntity<Long> getSaleById() {

        return ResponseEntity.ok(saleService.findMaxId());
    }

    @GetMapping("/get-items/{sale_id}")
    public ResponseEntity<List<SaleItem>> getSaleProductsById(@PathVariable("sale_id") Long saleId) {
        return ResponseEntity.ok(saleProductService.getAllBySale(saleId));
    }

    @DeleteMapping("/delete-items/{id}")
    public ResponseEntity<Void> deleteAllItems(@PathVariable final Long id) {
        saleProductService.deleteAllItems(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/add-items")
    @Transactional
    public ResponseEntity<Void> addItems(@RequestBody final List<SaleItem> items) {
        saleProductService.addItems(items);
        return ResponseEntity.noContent().build();

    }


}
