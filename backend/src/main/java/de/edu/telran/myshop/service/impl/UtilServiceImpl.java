package de.edu.telran.myshop.service.impl;

import de.edu.telran.myshop.entity.SaleItem;
import de.edu.telran.myshop.entity.SupplyItem;
import de.edu.telran.myshop.entity.Product;
import de.edu.telran.myshop.exception.ErrorMassage;
import de.edu.telran.myshop.exception.ProductNotFoundException;
import de.edu.telran.myshop.repository.ProductRepository;
import de.edu.telran.myshop.service.UtilService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UtilServiceImpl implements UtilService {
    private final ProductRepository productRepository;

    /**
     * Updates stock: the quantity of products based on the provided list of SupplyItem items.
     *
     * @param list The list of SupplyItem or Sale items containing the products to update.
     * @param add  True if the quantity should be added to the existing product quantity; false if it should be subtracted.
     * @throws ProductNotFoundException If a product in the list is not found in the product repository.
     */
    public <T> void updateProductsQuantity(List<T> list, boolean add) {
        list.forEach(item -> {
            if (item instanceof SupplyItem || item instanceof SaleItem) {
                Product p = null;
                if (item instanceof SupplyItem) {
                    p = productRepository.findById(((SupplyItem) item).getProduct().getId())
                            .orElseThrow(() -> new ProductNotFoundException(ErrorMassage.PRODUCT_NOT_FOUND_IN_INC_INVOICE));
                }
                if (item instanceof SaleItem) {
                    p = productRepository.findById(((SaleItem) item).getProduct().getId())
                            .orElseThrow(() -> new ProductNotFoundException(ErrorMassage.PRODUCT_NOT_FOUND_IN_DEC_INVOICE));
                }

                if (p != null) {
                    int quantity = (item instanceof SupplyItem) ? ((SupplyItem) item).getQuantity() : ((SaleItem) item).getQuantity();
                    p.setQuantity(add ? p.getQuantity() + quantity : p.getQuantity() - quantity);
                    productRepository.saveAndFlush(p);
                }
            } else {
                throw new IllegalArgumentException("Invalid type of InvoiceProduct item");
            }
        });
    }

}
