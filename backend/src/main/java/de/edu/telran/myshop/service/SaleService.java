package de.edu.telran.myshop.service;

import de.edu.telran.myshop.entity.Sale;

import java.util.List;

public interface SaleService {
    List<Sale> getAll();

    List<Sale> getAllSortByDateDesc();

    Sale create(final Sale sale);

    Sale update(final Sale sale);

    Sale getById(final Long saleId);

    void delete(final Long saleId);

    Long findMaxId();
}
