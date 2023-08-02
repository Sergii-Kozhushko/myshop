package de.edu.telran.myshop.repository;

import de.edu.telran.myshop.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DecInvoiceRepository extends JpaRepository<ProductCategory, Integer> {
    List<ProductCategory> findByActiveTrue();

    List<ProductCategory> findByActiveTrueOrderByNameAsc();
}
