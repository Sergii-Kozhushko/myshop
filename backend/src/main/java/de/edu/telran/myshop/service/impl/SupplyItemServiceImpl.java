/**
 * ProductService.java
 *
 * @author Sergii Kozhushko, sergiikozhushko@gmail.com
 * Date of creation: 27-Jun-2023 18:46
 */

package de.edu.telran.myshop.service.impl;

import de.edu.telran.myshop.entity.SupplyItem;
import de.edu.telran.myshop.repository.SupplyItemRepository;
import de.edu.telran.myshop.repository.SupplyRepository;
import de.edu.telran.myshop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SupplyItemServiceImpl {
    private final SupplyItemRepository supplyItemRepository;
    private final ProductRepository productRepository;
    private final SupplyRepository supplyRepository;
    private final UtilServiceImpl utilService;

    public List<SupplyItem> getAllBySupply(Long supplyId) {
        return supplyItemRepository.findItemsBySupplyId(supplyId);
    }

    public void deleteAllItems(Long supplyId) {
        List<SupplyItem> items = supplyItemRepository.
                findItemsBySupplyId(supplyId);
        utilService.updateProductsQuantity(items, false);
        supplyItemRepository.deleteItemsBySupplyId(supplyId);
    }


    public void addItems(List<SupplyItem> list) {
        utilService.updateProductsQuantity(list, true);
        supplyItemRepository.saveAll(list);
    }
}
