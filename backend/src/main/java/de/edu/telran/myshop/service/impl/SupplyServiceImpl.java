/**
 * ProductService.java
 *
 * @author Sergii Kozhushko, sergiikozhushko@gmail.com
 * Date of creation: 27-Jun-2023 18:46
 */

package de.edu.telran.myshop.service.impl;

import de.edu.telran.myshop.entity.Supply;
import de.edu.telran.myshop.exception.ErrorMassage;
import de.edu.telran.myshop.repository.SupplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SupplyServiceImpl {
    private final SupplyRepository supplyRepository;


    public List<Supply> getAll() {
        return supplyRepository.findAll();
    }


    public List<Supply> getAllSortByDateDesc() {
        return supplyRepository.findAllByOrderByCreatedAtDesc();
    }

    @Transactional
    public Supply create(final Supply supply) {
        return supplyRepository.save(supply);
    }

    //@Override
    @Transactional
    public Supply update(final Supply supply) {
        return supplyRepository.save(supply);
    }

    //@Override
    public Supply getById(final Long supplyId) throws Exception {
        return supplyRepository
                .findById(supplyId)
                .orElseThrow(() -> new RuntimeException(ErrorMassage.SUPPLY_NOT_FOUND));
        // TODO добавить эксепшен
    }


    //@Override
    @Transactional
    public void delete(final Long supplyId) throws Exception {

        if (!supplyRepository.findById(supplyId).isPresent()) {
            //throw new CustomerNotFoundException(ErrorMassage.CUSTOMER_NOT_FOUND);
            //TODO create exception
        } else {
            supplyRepository.deleteById(supplyId);
        }
    }

    public Long findMaxId() {
        return supplyRepository.maxSupplyId();
    }

}
