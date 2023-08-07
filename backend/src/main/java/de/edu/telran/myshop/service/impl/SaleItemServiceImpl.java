/**
 * ProductService.java
 *
 * @author Sergii Kozhushko, sergiikozhushko@gmail.com
 * Date of creation: 27-Jun-2023 18:46
 */

package de.edu.telran.myshop.service.impl;

import de.edu.telran.myshop.entity.SaleItem;
import de.edu.telran.myshop.repository.SaleItemRepository;
import de.edu.telran.myshop.repository.SaleRepository;
import de.edu.telran.myshop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SaleItemServiceImpl {
    private final SaleItemRepository saleItemRepository;
    private final ProductRepository productRepository;
    private final SaleRepository saleRepository;
    private final UtilServiceImpl utilService;

    public List<SaleItem> getAllBySale(Long saleId) {
        return saleItemRepository.findItemsBySaleId(saleId);
    }

    public void deleteAllItems(Long saleId) {
        List<SaleItem> items = saleItemRepository.findItemsBySaleId(saleId);
        utilService.updateProductsQuantity(items, true);
        saleItemRepository.deleteItemsBySaleId(saleId);
    }

    public void addItems(List<SaleItem> list) {
        utilService.updateProductsQuantity(list, false);
        saleItemRepository.saveAll(list);
    }


}
