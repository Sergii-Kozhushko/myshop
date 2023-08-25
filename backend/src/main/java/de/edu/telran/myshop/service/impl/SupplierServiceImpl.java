package de.edu.telran.myshop.service.impl;

import de.edu.telran.myshop.entity.Supplier;
import de.edu.telran.myshop.exception.ErrorMassage;
import de.edu.telran.myshop.exception.InvalidSupplierParameterException;
import de.edu.telran.myshop.repository.SupplierRepository;
import de.edu.telran.myshop.service.SupplierService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SupplierServiceImpl implements SupplierService {
    private final SupplierRepository supplierRepository;


    @Override
    public List<Supplier> getAll() {
        return supplierRepository.findAll();
    }

    @Override
    @Transactional
    public Supplier create(final Supplier supplier) {
        return supplierRepository.saveAndFlush(supplier);
    }

    @Override
    @Transactional
    public Supplier update(final Supplier supplier) throws InvalidSupplierParameterException {
        if (supplier.getId() == null) {
            throw new InvalidSupplierParameterException(ErrorMassage.INVALID_SUPPLIER_ID);
        }
        // check if Product exists
        supplierRepository.findById(supplier.getId()).orElseThrow(() ->
                new InvalidSupplierParameterException(ErrorMassage.SUPPLIER_NOT_FOUND));

        if (supplier.getName() == null) {
            throw new InvalidSupplierParameterException(ErrorMassage.SUPPLIER_NAME_EMPTY);
        }
        return supplierRepository.save(supplier);
    }

    @Override
    public Supplier getById(final Long supplierId) throws InvalidSupplierParameterException {
        return supplierRepository
                .findById(supplierId)
                .orElseThrow(() -> new InvalidSupplierParameterException(ErrorMassage.SUPPLIER_NOT_FOUND));
    }

    @Override
    @Transactional
    public void delete(final Long supplierId) throws InvalidSupplierParameterException {
        if (supplierRepository.findById(supplierId).isEmpty()) {
            throw new InvalidSupplierParameterException(ErrorMassage.SUPPLIER_NOT_FOUND);
        }
        supplierRepository.deleteById(supplierId);
    }

    /**
     * Finds and returns the maximum ID among the suppliers in the repository.
     * It helps to show future id when creating new document
     *
     * @return The maximum ID among the suppliers.
     */
    @Override
    public Long findMaxId() {
        return supplierRepository.maxSupplierId();
    }

}
