package de.edu.telran.myshop.service.impl;

import de.edu.telran.myshop.entity.Supply;
import de.edu.telran.myshop.exception.CustomerNotFoundException;
import de.edu.telran.myshop.exception.ErrorMassage;
import de.edu.telran.myshop.exception.SupplyNotFoundException;
import de.edu.telran.myshop.repository.SupplyRepository;
import de.edu.telran.myshop.service.SupplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SupplyServiceImpl implements SupplyService {
    private final SupplyRepository supplyRepository;

    @Override
    public List<Supply> getAll() {
        return supplyRepository.findAll();
    }

    @Override
    public List<Supply> getAllSortByDateDesc() {
        return supplyRepository.findAllByOrderByCreatedAtDesc();
    }

    @Override
    @Transactional
    public Supply create(final Supply supply) {
        return supplyRepository.save(supply);
    }

    @Override
    @Transactional
    public Supply update(final Supply supply) {
        return supplyRepository.save(supply);
    }

    @Override
    public Supply getById(final Long supplyId) throws RuntimeException {
        return supplyRepository
                .findById(supplyId)
                .orElseThrow(() -> new RuntimeException(ErrorMassage.SUPPLY_NOT_FOUND));
        // TODO add custom exception
    }


    @Override
    @Transactional
    public void delete(final Long supplyId) throws SupplyNotFoundException {
        supplyRepository.findById(supplyId).orElseThrow(() ->
                new SupplyNotFoundException(ErrorMassage.SUPPLY_NOT_FOUND));
        supplyRepository.deleteById(supplyId);
    }

    /**
     * Finds and returns the maximum ID among the supplies in the repository.
     *
     * @return The maximum ID among the supplies.
     */
    @Override
    public Long findMaxId() {
        return supplyRepository.maxSupplyId();
    }

}
