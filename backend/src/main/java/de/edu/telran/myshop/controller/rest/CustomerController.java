/**
 * ProductController.java
 *
 * @author Sergii Kozhushko, sergiikozhushko@gmail.com
 * Date of creation: 27-Jun-2023 18:48
 */

package de.edu.telran.myshop.controller.rest;

import de.edu.telran.myshop.dto.CreateCustomerDto;
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

import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping(CustomerController.MAIN_PATH)
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerServiceImpl customerService;
    public static final String MAIN_PATH = "/customer";
    public static final String ID_COLUMN = "id";

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

        Sort sort = Sort.by(direction, customerSearchValues.getSortColumn(), ID_COLUMN);
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
    public ResponseEntity<Customer> createCustomer(@RequestBody final CreateCustomerDto customerDto)
            throws URISyntaxException {
        
        return ResponseEntity
                .created(new URI(MAIN_PATH))
                .body(customerService.create(customerDto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable final Long id) throws Exception {

        customerService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable("id") Long id
    ) throws Exception {

        return ResponseEntity.ok(customerService.getById(id));
    }

    @PutMapping("/update")
    public ResponseEntity<Customer> updateCustomer(@RequestBody final Customer customer
    ) throws Exception {

        return ResponseEntity.created(new URI(MAIN_PATH))
                .body(customerService.update(customer));
    }

}
