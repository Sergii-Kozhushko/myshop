package de.edu.telran.myshop.service.impl;

import de.edu.telran.myshop.entity.SaleItem;
import de.edu.telran.myshop.repository.SaleItemRepository;
import de.edu.telran.myshop.repository.SaleRepository;
import de.edu.telran.myshop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import de.edu.telran.myshop.service.*;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SaleItemServiceImpl implements SaleItemService {
    private final SaleItemRepository saleItemRepository;
    private final ProductRepository productRepository;
    private final SaleRepository saleRepository;
    private final UtilServiceImpl utilService;

    @Override
    public List<SaleItem> getAllBySale(Long saleId) {
        return saleItemRepository.findItemsBySaleId(saleId);
    }

    /**
     * Deletes all sale items associated with a given sale.
     * Updates the product quantities accordingly.
     *
     * @param saleId The ID of the sale.
     */
    @Override
    public void deleteAllItems(Long saleId) {
        List<SaleItem> items = saleItemRepository.findItemsBySaleId(saleId);
        utilService.updateProductsQuantity(items, true);
        saleItemRepository.deleteItemsBySaleId(saleId);
    }

    /**
     * Adds a list of sale items to the repository.
     * Updates the product quantities accordingly.
     *
     * @param list The list of SaleItem objects to add.
     */
    @Override
    public void addItems(List<SaleItem> list) {
        utilService.updateProductsQuantity(list, false);
        saleItemRepository.saveAll(list);
    }


}
