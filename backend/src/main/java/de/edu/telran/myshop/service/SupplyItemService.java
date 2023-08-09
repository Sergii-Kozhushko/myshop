package de.edu.telran.myshop.service;

import de.edu.telran.myshop.entity.SupplyItem;

import java.util.List;

public interface SupplyItemService {
    List<SupplyItem> getAllBySupply(Long supplyId);

    void deleteAllItems(Long supplyId);

    void addItems(List<SupplyItem> list);

}
