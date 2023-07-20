/**
 * ProductServiceInterface.java
 *
 * @author Sergii Kozhushko, sergiikozhushko@gmail.com
 * Date of creation: 08-Jul-2023 12:33
 */

package de.edu.telran.myshop.service.interfaces;

import de.edu.telran.myshop.dto.CreateCustomerDto;
import de.edu.telran.myshop.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface CustomerService {
   public List<Customer> getAll();
   public Customer create(final CreateCustomerDto createCustomerDto);
   public Customer update(final Customer customer) throws Exception;
   public Customer getById(final Long customerId) throws Exception;
   public void delete(final Long customerId) throws Exception;
   public Page<Customer> findByParams(String name, String email, String phone, PageRequest paging);

}
