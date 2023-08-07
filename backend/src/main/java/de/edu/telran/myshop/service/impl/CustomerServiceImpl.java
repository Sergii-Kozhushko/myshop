/**
 * ProductService.java
 *
 * @author Sergii Kozhushko, sergiikozhushko@gmail.com
 * Date of creation: 27-Jun-2023 18:46
 */

package de.edu.telran.myshop.service.impl;

import de.edu.telran.myshop.dto.CreateCustomerDto;
import de.edu.telran.myshop.entity.Customer;
import de.edu.telran.myshop.exception.CustomerNotFoundException;
import de.edu.telran.myshop.exception.ErrorMassage;
import de.edu.telran.myshop.exception.InvalidCustomerParameterException;
import de.edu.telran.myshop.mapper.CustomerMapper;
import de.edu.telran.myshop.repository.CustomerRepository;
import de.edu.telran.myshop.service.interfaces.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final EntityManager entityManager;


    @Override
    public List<Customer> getAll() {
        return customerRepository.findAll();
    }

    @Override
    @Transactional
    public Customer create(final Customer createCustomer) {
        return customerRepository.saveAndFlush(createCustomer);
    }

    @Override
    @Transactional
    public Customer update(final Customer customer) throws Exception {
        if (customer.getId() == null) {
            throw new InvalidCustomerParameterException(ErrorMassage.CUSTOMER_ID_EMPTY);
        }
        // check if Product exists
        customerRepository.findById(customer.getId()).orElseThrow(() ->
                new CustomerNotFoundException(ErrorMassage.CUSTOMER_NOT_FOUND));

        if (customer.getName() == null) {
            throw new InvalidCustomerParameterException(ErrorMassage.CUSTOMER_NAME_EMPTY);
        }
        Customer result = customerRepository.saveAndFlush(customer);
        // force jpa to get data from db, instead of cache
        entityManager.refresh(result);

        return result;
        //return customerRepository.save(customer);
    }

    @Override
    public Customer getById(final Long customerId) throws Exception {
        return customerRepository
                .findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(ErrorMassage.CUSTOMER_NOT_FOUND));


    }


    @Override
    @Transactional
    public void delete(final Long customerId) throws Exception {

        if (!customerRepository.findById(customerId).isPresent()) {
            throw new CustomerNotFoundException(ErrorMassage.CUSTOMER_NOT_FOUND);
        }
        customerRepository.deleteById(customerId);
    }

    @Override
    //@Cacheable(cacheNames = "products")
    public Page<Customer> findByParams(String name, String email, String phone, PageRequest paging) {
        return customerRepository.findByParams(name, email, phone, paging);
    }

}
