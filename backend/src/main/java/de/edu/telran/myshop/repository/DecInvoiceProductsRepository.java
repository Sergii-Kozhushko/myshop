package de.edu.telran.myshop.repository;

import de.edu.telran.myshop.entity.DecInvoice;
import de.edu.telran.myshop.entity.DecInvoiceProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface DecInvoiceProductsRepository extends JpaRepository<DecInvoiceProduct, Long> {
    @Query("SELECT d FROM DecInvoiceProduct d where " +
            "(:decinvoice_id is null or d.decInvoice.id=:decinvoice_id)"
    )
    List<DecInvoiceProduct> findDecInvoiceProductById(@Param("decinvoice_id") Long decInvoiceId);

    @Modifying
    @Transactional
    @Query("DELETE FROM DecInvoiceProduct d WHERE d.decInvoice.id = :decinvoice_id")
    void clearDecInvoiceItems(@Param("decinvoice_id") Long decInvoiceId);

}
