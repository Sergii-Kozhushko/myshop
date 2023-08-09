package de.edu.telran.myshop.service;

import de.edu.telran.myshop.dto.CreateCustomerDto;
import de.edu.telran.myshop.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface CustomerService {
    List<Customer> getAll();

    Customer create(final Customer createCustomerDto);

    Customer update(final Customer customer) throws Exception;

    Customer getById(final Long customerId) throws Exception;

    void delete(final Long customerId) throws Exception;

    public Page<Customer> findByParams(String name, String email, String phone, PageRequest paging);

}
