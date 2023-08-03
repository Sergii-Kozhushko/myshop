package de.edu.telran.myshop.repository;

import de.edu.telran.myshop.entity.DecInvoice;
import de.edu.telran.myshop.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DecInvoiceRepository extends JpaRepository<DecInvoice, Long> {
    List<DecInvoice> findAllByOrderByCreatedAtDesc();

}
