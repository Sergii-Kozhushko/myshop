package de.edu.telran.myshop.service.impl;

import de.edu.telran.myshop.entity.Customer;
import de.edu.telran.myshop.exception.CustomerNotFoundException;
import de.edu.telran.myshop.exception.ErrorMassage;
import de.edu.telran.myshop.exception.InvalidCustomerParameterException;
import de.edu.telran.myshop.mapper.CustomerMapper;
import de.edu.telran.myshop.repository.CustomerRepository;
import de.edu.telran.myshop.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
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


    /**
     * Retrieves a list of all customers from the database.
     *
     * @return A list of Customer objects representing all customers.
     */
    @Override
    public List<Customer> getAll() {
        return customerRepository.findAllByOrderByNameAsc();

    }

    /**
     * Creates a new customer in the database.
     *
     * @param createCustomer The Customer object to be created.
     * @return The Customer object that has been created.
     */
    @Override
    @Transactional
    public Customer create(final Customer createCustomer) {
        return customerRepository.saveAndFlush(createCustomer);
    }

    /**
     * Updates the information of an existing customer in the database.
     *
     * @param customer The Customer object containing the updated information.
     * @return The updated Customer object.
     * @throws InvalidCustomerParameterException If the customer ID is empty or if the customer name is empty.
     * @throws CustomerNotFoundException         If the customer with the given ID is not found.
     */
    @Override
    @Transactional
    public Customer update(final Customer customer)
            throws InvalidCustomerParameterException, CustomerNotFoundException {

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
    }

    /**
     * Retrieves a customer by their unique ID.
     *
     * @param customerId The ID of the customer to retrieve.
     * @return The Customer object associated with the provided ID.
     * @throws CustomerNotFoundException If the customer with the given ID is not found.
     */
    @Override
    public Customer getById(final Long customerId) throws CustomerNotFoundException {
        return customerRepository
                .findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(ErrorMassage.CUSTOMER_NOT_FOUND));
    }

    /**
     * Deletes a customer from the database.
     *
     * @param customerId The ID of the customer to be deleted.
     * @throws CustomerNotFoundException If the customer with the given ID is not found.
     */
    @Override
    @Transactional
    public void delete(final Long customerId) throws CustomerNotFoundException {

        customerRepository.findById(customerId).orElseThrow(() ->
                new CustomerNotFoundException(ErrorMassage.CUSTOMER_NOT_FOUND));
        customerRepository.deleteById(customerId);
    }

    /**
     * Retrieves a page of customers based on provided parameters.
     *
     * @param name   The name of the customer (optional).
     * @param email  The email of the customer (optional).
     * @param phone  The phone number of the customer (optional).
     * @param paging The pagination information.
     * @return A Page of Customer objects that match the provided parameters.
     */
    @Override
    public Page<Customer> findByParams(String name, String email, String phone, PageRequest paging) {
        return customerRepository.findByParams(name, email, phone, paging);
    }

}
