/**
 * ProductService.java
 *
 * @author Sergii Kozhushko, sergiikozhushko@gmail.com
 * Date of creation: 27-Jun-2023 18:46
 */

package de.edu.telran.myshop.service.impl;

import de.edu.telran.myshop.entity.Sale;
import de.edu.telran.myshop.exception.ErrorMassage;
import de.edu.telran.myshop.repository.SaleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SaleServiceImpl {
    private final SaleRepository saleRepository;


    public List<Sale> getAll() {
        return saleRepository.findAll();
    }

    public List<Sale> getAllSortByDateDesc() {
        return saleRepository.findAllByOrderByCreatedAtDesc();
    }


    @Transactional
    public Sale create(final Sale sale) {
        return saleRepository.save(sale);
    }

    //@Override
    @Transactional
    public Sale update(final Sale sale) throws Exception {
        return saleRepository.save(sale);
    }

    //@Override
    public Sale getById(final Long saleId) throws Exception {
        return saleRepository
                .findById(saleId)
                .orElseThrow(() -> new RuntimeException(ErrorMassage.CUSTOMER_NOT_FOUND));
        // TODO вставить сообщение об ошибке
    }


    //@Override
    @Transactional
    public void delete(final Long saleId) throws Exception {

        if (!saleRepository.findById(saleId).isPresent()) {
            //throw new CustomerNotFoundException(ErrorMassage.CUSTOMER_NOT_FOUND);
            //TODO create exception
            return;
        }
        saleRepository.deleteById(saleId);
    }

    public Long findMaxId() {
        return saleRepository.maxSaleId();
    }


    //@Override
    //@Cacheable(cacheNames = "products")
//    public Page<Customer> findByParams(String name, String email, String phone, PageRequest paging) {
//        return customerRepository.findByParams(name, email, phone, paging);
//    }

}
