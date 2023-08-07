/**
 * ProductService.java
 *
 * @author Sergii Kozhushko, sergiikozhushko@gmail.com
 * Date of creation: 27-Jun-2023 18:46
 */

package de.edu.telran.myshop.service.impl;

import de.edu.telran.myshop.entity.Supplier;
import de.edu.telran.myshop.exception.ErrorMassage;
import de.edu.telran.myshop.exception.InvalidSupplierParameterException;
import de.edu.telran.myshop.repository.SupplierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SupplierServiceImpl {
    private final SupplierRepository supplierRepository;
    // private final SupplierMapper supplierMapper;
    // private final EntityManager entityManager;


    // @Override
    public List<Supplier> getAll() {
        return supplierRepository.findAll();
    }

    // @Override
    @Transactional
    public Supplier create(final Supplier supplier) {
        return supplierRepository.saveAndFlush(supplier);
    }

    // @Override
    @Transactional
    public Supplier update(final Supplier supplier) throws Exception {
        if (supplier.getId() == null) {
            throw new InvalidSupplierParameterException(ErrorMassage.INVALID_SUPPLIER_ID);
        }
        // check if Product exists
        supplierRepository.findById(supplier.getId()).orElseThrow(() ->
                new InvalidSupplierParameterException(ErrorMassage.SUPPLIER_NOT_FOUND));

        if (supplier.getName() == null) {
            throw new InvalidSupplierParameterException(ErrorMassage.SUPPLIER_NAME_EMPTY);
        }
        // Supplier result = supplierRepository.saveAndFlush(supplier);
        // force jpa to get data from db, instead of cache
        // entityManager.refresh(result);

        // return result;
        return supplierRepository.save(supplier);
    }

    // @Override
    public Supplier getById(final Long supplierId) throws Exception {
        return supplierRepository
                .findById(supplierId)
                .orElseThrow(() -> new InvalidSupplierParameterException(ErrorMassage.SUPPLIER_NOT_FOUND));


    }


    // @Override
    @Transactional
    public void delete(final Long supplierId) throws Exception {
        if (!supplierRepository.findById(supplierId).isPresent()) {
            throw new InvalidSupplierParameterException(ErrorMassage.SUPPLIER_NOT_FOUND);
        }
        supplierRepository.deleteById(supplierId);
    }

    public Long findMaxId() {
        return supplierRepository.maxSupplierId();
    }

    // @Override
    //@Cacheable(cacheNames = "products")
//    public Page<Supplier> findByParams(String name, String email, String phone, PageRequest paging) {
//        return supplierRepository.findByParams(name, email, phone, paging);
//    }

}
