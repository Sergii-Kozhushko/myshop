package de.edu.telran.myshop.service;

import de.edu.telran.myshop.entity.Supplier;

import java.util.List;

public interface SupplierService {
    List<Supplier> getAll();

    Supplier create(final Supplier supplier);

    Supplier update(final Supplier supplier);

    Supplier getById(final Long supplierId);

    void delete(final Long supplierId);

    Long findMaxId();
}
