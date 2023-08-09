package de.edu.telran.myshop.service;

import java.util.List;

public interface UtilService {
    <T> void updateProductsQuantity(List<T> list, boolean add);
}
