package de.edu.telran.myshop.repository;

import de.edu.telran.myshop.entity.Supply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplyRepository extends JpaRepository<Supply, Long> {

    @Query("SELECT MAX (d.id) FROM Supply d")
    Long maxSupplyId();

    List<Supply> findAllByOrderByCreatedAtDesc();


}
