package de.edu.telran.myshop.service.impl;

import de.edu.telran.myshop.entity.Customer;
import de.edu.telran.myshop.exception.CustomerNotFoundException;
import de.edu.telran.myshop.exception.InvalidCustomerParameterException;
import de.edu.telran.myshop.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    @Mock
    CustomerRepository customerRepository;


    @Mock
    EntityManager entityManager;

    @InjectMocks
    CustomerServiceImpl customerService;

    private static List<Customer> initCustomers = new ArrayList<>();

    @BeforeAll
    static void initData() {

        initCustomers = List.of(
                Customer.builder().
                        name("John Travolta")
                        .email("john1@gmail.com")
                        .address("121 Amazon St, Seattle, WA")
                        .phone("+1 555-123-4667")
                        .acceptSMSList(true)
                        .build(),
                Customer.builder().
                        name("Michael Maers")
                        .email("mmaers@gmail.com")
                        .address("555 Willow Lane, Somewhere, USA")
                        .phone("+1 255-123-4667")
                        .acceptSMSList(true)
                        .build()
        );
    }

    @Test
    @DisplayName("return list of all customers")
    void getAll_ReturnsListCustomer() {
        // given

        when(this.customerRepository.findAllByOrderByNameAsc()).thenReturn(initCustomers);

        // when
        var result = this.customerService.getAll();

        // then
        assertEquals(result, initCustomers);
    }

    @Test
    @DisplayName("create new Customer")
    @Transactional
    public void testCreateCustomer_ValidCustomer_Success() {
        Customer customer = new Customer();
        customer.setName("John Doe");

        when(customerRepository.saveAndFlush(any(Customer.class))).thenReturn(customer);

        Customer createdCustomer = customerService.create(customer);

        assertEquals(createdCustomer.getName(), "John Doe");
    }

    @Test
    void createCustomer_ShouldBeTransactional() throws NoSuchMethodException {
        // Arrange
        // Act
        boolean isTransactional = CustomerServiceImpl.class.getMethod("create", Customer.class)
                .isAnnotationPresent(Transactional.class);

        // Assert
        assertTrue(isTransactional); // Check if method has @Transactional
    }

    @Test
    @Transactional
    public void testUpdateCustomer_ValidCustomer_Success() {
        Customer existingCustomer = new Customer();
        existingCustomer.setId(1L);
        existingCustomer.setName("John Doe");

        Customer updatedCustomer = new Customer();
        updatedCustomer.setId(1L);
        updatedCustomer.setName("Updated Name");

        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(existingCustomer));
        when(customerRepository.saveAndFlush(any(Customer.class))).thenReturn(updatedCustomer);

        Customer result = customerService.update(updatedCustomer);

        assertThat(result.getName()).isEqualTo("Updated Name");
    }

    @Test
    public void testUpdateCustomer_CustomerIdEmpty_ThrowsException() {
        Customer customer = new Customer();

        assertThrows(InvalidCustomerParameterException.class, () -> customerService.update(customer));
    }

    @Test
    public void testUpdateCustomer_CustomerNotFound_ThrowsException() {
        Customer customer = new Customer();
        customer.setId(1L);

        when(customerRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(CustomerNotFoundException.class, () -> customerService.update(customer));
    }

    @Test
    public void testUpdateCustomer_CustomerNameEmpty_ThrowsException() {
        Customer customer = new Customer();
        customer.setId(1L);

        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(customer));

        assertThrows(InvalidCustomerParameterException.class, () -> customerService.update(customer));
    }

    @Test
    public void testGetCustomerById_ValidId_ReturnsCustomer() {
        Long customerId = 1L;
        Customer customer = new Customer();
        customer.setId(customerId);

        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(customer));

        Customer result = customerService.getById(customerId);

        assertThat(result).isEqualTo(customer);
    }

    @Test
    public void testGetCustomerById_CustomerNotFound_ThrowsException() {
        Long customerId = 1L;

        when(customerRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(CustomerNotFoundException.class, () -> customerService.getById(customerId));
    }

    @Test
    public void testDeleteCustomer_ValidId_DeletesCustomer() {
        Long customerId = 1L;

        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(new Customer()));

        customerService.delete(customerId);

        verify(customerRepository).deleteById(customerId);
    }

}