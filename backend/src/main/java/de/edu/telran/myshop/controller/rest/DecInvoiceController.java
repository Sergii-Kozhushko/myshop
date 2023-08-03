/**
 * ProductController.java
 *
 * @author Sergii Kozhushko, sergiikozhushko@gmail.com
 * Date of creation: 27-Jun-2023 18:48
 */

package de.edu.telran.myshop.controller.rest;

import de.edu.telran.myshop.dto.CreateCustomerDto;
import de.edu.telran.myshop.entity.Customer;
import de.edu.telran.myshop.entity.DecInvoice;
import de.edu.telran.myshop.entity.DecInvoiceProduct;
import de.edu.telran.myshop.search.CustomerSearchValues;
import de.edu.telran.myshop.service.impl.CustomerServiceImpl;
import de.edu.telran.myshop.service.impl.DecInvoiceProductsServiceImpl;
import de.edu.telran.myshop.service.impl.DecInvoiceServiceImpl;
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
@RequestMapping(DecInvoiceController.MAIN_PATH)
@RequiredArgsConstructor
public class DecInvoiceController {
    private final DecInvoiceServiceImpl decInvoiceService;
    private final DecInvoiceProductsServiceImpl decInvoiceProductsService;

    public static final String MAIN_PATH = "/decinvoice";
    public static final String ID_COLUMN = "id";

    @GetMapping("/all")
    public ResponseEntity<List<DecInvoice>> getAllDecInvoices() {

        return ResponseEntity.ok(decInvoiceService.getAll());
    }

    @GetMapping("/all-sort-date-desc")
    public ResponseEntity<List<DecInvoice>> getAllDecInvoicesSortDateDesc() {
        return ResponseEntity.ok(decInvoiceService.getAllSortByDateDesc());
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
    public ResponseEntity<DecInvoice> createDecInvoice(@RequestBody final DecInvoice decInvoice)
            throws URISyntaxException {

        return ResponseEntity
                .created(new URI(MAIN_PATH))
                .body(decInvoiceService.create(decInvoice));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteDecInvoice(@PathVariable final Long id) throws Exception {

        decInvoiceService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<DecInvoice> getDecInvoiceById(@PathVariable("id") Long id
    ) throws Exception {

        return ResponseEntity.ok(decInvoiceService.getById(id));
    }

    @PutMapping("/update")
    public ResponseEntity<DecInvoice> updateDecInvoice(@RequestBody final DecInvoice decInvoice
    ) throws Exception {

        return ResponseEntity.created(new URI(MAIN_PATH))
                .body(decInvoiceService.update(decInvoice));
    }

    @GetMapping("/get-products/{id}")
    public ResponseEntity<List<DecInvoiceProduct>> getDecInvoiceProductsById(@PathVariable("id") Long id
    ) throws Exception {

        return ResponseEntity.ok(decInvoiceProductsService.getAllByDecInvoice(id));
    }

    @DeleteMapping("/delete-items/{id}")
    public ResponseEntity<Void> deleteAllItems(@PathVariable final Long id) throws Exception {

        decInvoiceProductsService.deleteAllItems(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/add-items")
    @Transactional
    public ResponseEntity<Void> addItems(@RequestBody final List<DecInvoiceProduct> items)
            throws URISyntaxException {

        decInvoiceProductsService.addItems(items);
        return ResponseEntity.noContent().build();
        
    }


}
