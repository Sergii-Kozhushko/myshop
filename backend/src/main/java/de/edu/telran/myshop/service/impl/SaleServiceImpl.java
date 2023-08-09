package de.edu.telran.myshop.service.impl;

import de.edu.telran.myshop.entity.Sale;
import de.edu.telran.myshop.exception.CustomerNotFoundException;
import de.edu.telran.myshop.exception.ErrorMassage;
import de.edu.telran.myshop.repository.SaleRepository;
import de.edu.telran.myshop.service.SaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SaleServiceImpl implements SaleService {
    private final SaleRepository saleRepository;

    @Override
    public List<Sale> getAll() {
        return saleRepository.findAll();
    }

    @Override
    public List<Sale> getAllSortByDateDesc() {
        return saleRepository.findAllByOrderByCreatedAtDesc();
    }


    @Override
    @Transactional
    public Sale create(final Sale sale) {
        return saleRepository.save(sale);
    }

    @Override
    @Transactional
    public Sale update(final Sale sale) {
        return saleRepository.save(sale);
    }

    @Override
    public Sale getById(final Long saleId) throws CustomerNotFoundException {
        return saleRepository
                .findById(saleId)
                .orElseThrow(() -> new CustomerNotFoundException(ErrorMassage.CUSTOMER_NOT_FOUND));
    }


    @Override
    @Transactional
    public void delete(final Long saleId) throws CustomerNotFoundException {
        saleRepository.findById(saleId).orElseThrow(() ->
                new CustomerNotFoundException(ErrorMassage.CUSTOMER_NOT_FOUND));
        saleRepository.deleteById(saleId);
    }

    @Override
    public Long findMaxId() {
        return saleRepository.maxSaleId();
    }

}
