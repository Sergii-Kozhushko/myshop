package de.edu.telran.myshop.repository;

import de.edu.telran.myshop.entity.SupplyItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface SupplyItemRepository extends JpaRepository<SupplyItem, Long> {

    @Query("SELECT i FROM SupplyItem i where (:supply_id is null or i.supply.id=:supply_id)")
    List<SupplyItem> findItemsBySupplyId(@Param("supply_id") Long supplyId);

    @Modifying
    @Transactional
    @Query("DELETE FROM SupplyItem i WHERE i.supply.id = :supply_id")
    void deleteItemsBySupplyId(@Param("supply_id") Long supplyId);

}