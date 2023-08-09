package de.edu.telran.myshop.service;

import de.edu.telran.myshop.entity.Supply;

import java.util.List;

public interface SupplyService {

    List<Supply> getAll();

    List<Supply> getAllSortByDateDesc();

    Supply create(Supply supply);

    Supply update(Supply supply);

    Supply getById(Long supplyId) throws Exception;

    void delete(Long supplyId) throws Exception;

    Long findMaxId();
}

