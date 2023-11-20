package de.edu.telran.myshop.repository;

import de.edu.telran.myshop.entity.SaleItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface SaleItemRepository extends JpaRepository<SaleItem, Long> {

    @Query("SELECT d FROM SaleItem d where "
            + "(:sale_id is null or d.sale.id=:sale_id)"
    )
    List<SaleItem> findItemsBySaleId(@Param("sale_id") Long saleId);

    @Modifying
    @Transactional
    @Query("DELETE FROM SaleItem d WHERE d.sale.id = :sale_id")
    void deleteItemsBySaleId(@Param("sale_id") Long saleId);
}