/**
 * ProductService.java
 *
 * @author Sergii Kozhushko, sergiikozhushko@gmail.com
 * Date of creation: 27-Jun-2023 18:46
 */

package de.edu.telran.myshop.service.impl;

import de.edu.telran.myshop.dto.CreateCustomerDto;
import de.edu.telran.myshop.entity.Customer;
import de.edu.telran.myshop.entity.DecInvoice;
import de.edu.telran.myshop.exception.CustomerNotFoundException;
import de.edu.telran.myshop.exception.ErrorMassage;
import de.edu.telran.myshop.exception.InvalidCustomerParameterException;
import de.edu.telran.myshop.mapper.CustomerMapper;
import de.edu.telran.myshop.repository.CustomerRepository;
import de.edu.telran.myshop.repository.DecInvoiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DecInvoiceServiceImpl {
    private final DecInvoiceRepository decInvoiceRepository;
    //private final CustomerMapper customerMapper;
    //private final EntityManager entityManager;


    public List<DecInvoice> getAll() {
        return decInvoiceRepository.findAll();
    }

    public List<DecInvoice> getAllSortByDateDesc() {
        return decInvoiceRepository.findAllByOrderByCreatedAtDesc();
    }


    @Transactional
    public DecInvoice create(final DecInvoice decInvoice) {
        return decInvoiceRepository.save(decInvoice);
    }

    //@Override
    @Transactional
    public DecInvoice update(final DecInvoice decInvoice) throws Exception {

        // check if Product exists


        DecInvoice result = decInvoiceRepository.save(decInvoice);
        // force jpa to get data from db, instead of cache
        //entityManager.refresh(result);

        return result;
        //return customerRepository.save(customer);
    }

    //@Override
    public DecInvoice getById(final Long decInvoiceId) throws Exception {
        return decInvoiceRepository
                .findById(decInvoiceId)
                .orElseThrow(() -> new RuntimeException(ErrorMassage.CUSTOMER_NOT_FOUND));
        // TODO вставить сообщение об ошибке
    }


    //@Override
    @Transactional
    public void delete(final Long decInvoiceId) throws Exception {

        if (!decInvoiceRepository.findById(decInvoiceId).isPresent()) {
            //throw new CustomerNotFoundException(ErrorMassage.CUSTOMER_NOT_FOUND);
            //TODO create exception
        }
        decInvoiceRepository.deleteById(decInvoiceId);
    }


    //@Override
    //@Cacheable(cacheNames = "products")
//    public Page<Customer> findByParams(String name, String email, String phone, PageRequest paging) {
//        return customerRepository.findByParams(name, email, phone, paging);
//    }

}
