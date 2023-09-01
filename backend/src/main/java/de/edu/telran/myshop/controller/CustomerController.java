package de.edu.telran.myshop.controller;

import de.edu.telran.myshop.config.URILinks;
import de.edu.telran.myshop.entity.Customer;
import de.edu.telran.myshop.search.CustomerSearchValues;
import de.edu.telran.myshop.service.impl.CustomerServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping(URILinks.CUSTOMER_URI)
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerServiceImpl customerService;


    @GetMapping("/all")
    public ResponseEntity<List<Customer>> getAllCustomers() {

        return ResponseEntity.ok(customerService.getAll());
    }

    @PostMapping("/search")
    public ResponseEntity<Page<Customer>> search(@RequestBody CustomerSearchValues customerSearchValues) throws ParseException {

        // preparing data for page view
        String sortDirection = customerSearchValues.getSortDirection();
        Sort.Direction direction = sortDirection == null || sortDirection.trim().length() == 0 ||
                sortDirection.trim().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;

        Sort sort = Sort.by(direction, customerSearchValues.getSortColumn(), URILinks.ID_COLUMN);
        Integer pageNumber = customerSearchValues.getPageNumber() != null ? customerSearchValues.getPageNumber() : 0;
        PageRequest pageRequest = PageRequest.of(customerSearchValues.getPageNumber(), customerSearchValues.getPageSize(), sort);
        Page<Customer> result = customerService.findByParams(
                customerSearchValues.getName(),
                customerSearchValues.getEmail(),
                customerSearchValues.getPhone(), pageRequest);

        return ResponseEntity.ok(result);
    }

    @PostMapping("/add")
    @Transactional
    public ResponseEntity<?> createCustomer(@RequestBody @Valid final Customer customer)
            throws URISyntaxException {
        return ResponseEntity
                .created(new URI(URILinks.PRODUCT_URI))
                .body(customerService.create(customer));
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable final Long id) {

        customerService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable("id") Long id
    ) {

        return ResponseEntity.ok(customerService.getById(id));
    }

    @PutMapping("/update")
    public ResponseEntity<Customer> updateCustomer(@RequestBody final Customer customer
    ) throws Exception {

        return ResponseEntity.created(new URI(URILinks.PRODUCT_URI))
                .body(customerService.update(customer));
    }

}
