package de.edu.telran.myshop.repository;

import de.edu.telran.myshop.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {

    // all documents sorted by date
    List<Sale> findAllByOrderByCreatedAtDesc();

    @Query("SELECT MAX (d.id) FROM Sale d"
    )
    Long maxSaleId();

}
