package de.edu.telran.myshop.service.impl;

import de.edu.telran.myshop.entity.SupplyItem;
import de.edu.telran.myshop.repository.SupplyItemRepository;
import de.edu.telran.myshop.repository.SupplyRepository;
import de.edu.telran.myshop.repository.ProductRepository;
import de.edu.telran.myshop.service.SupplyItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implements the SupplyItemService interface to provide operations related to supply items to shop.
 * <p>
 * This service handles the operations for retrieving, adding, and deleting supply items
 * associated with a supply, and also updates the quantity of products accordingly.
 * Additionally, it interacts with the repository to perform these operations.
 *
 * @author Sergii Kozhushko sergiikozhushko@gmail.com
 */
@Service
@RequiredArgsConstructor
public class SupplyItemServiceImpl implements SupplyItemService {

    private final SupplyItemRepository supplyItemRepository;
    private final UtilServiceImpl utilService;

    @Override
    public List<SupplyItem> getAllBySupply(Long supplyId) {
        return supplyItemRepository.findItemsBySupplyId(supplyId);
    }

    /**
     * Deletes all supply items associated with a given supply.
     * Updates the product quantities accordingly.
     *
     * @param supplyId The ID of the supply.
     */
    @Override
    public void deleteAllItems(Long supplyId) {
        List<SupplyItem> items = supplyItemRepository.findItemsBySupplyId(supplyId);
        utilService.updateProductsQuantity(items, false);
        supplyItemRepository.deleteItemsBySupplyId(supplyId);
    }

    /**
     * Adds a list of supply items to the repository.
     * Updates the product quantities accordingly.
     *
     * @param list The list of SupplyItem objects to add.
     */
    @Override
    public void addItems(List<SupplyItem> list) {
        utilService.updateProductsQuantity(list, true);
        supplyItemRepository.saveAll(list);
    }
}