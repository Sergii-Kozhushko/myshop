package de.edu.telran.myshop.service;

import de.edu.telran.myshop.entity.SaleItem;

import java.util.List;

public interface SaleItemService {
    List<SaleItem> getAllBySale(Long saleId);

    void deleteAllItems(Long saleId);

    void addItems(List<SaleItem> list);
}
