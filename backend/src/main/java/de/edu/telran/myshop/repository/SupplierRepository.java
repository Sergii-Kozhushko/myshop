/**
 * ProductRepository.java
 *
 * @author Sergii Kozhushko, sergiikozhushko@gmail.com
 * Date of creation: 27-Jun-2023 18:44
 */

package de.edu.telran.myshop.repository;

import de.edu.telran.myshop.entity.Customer;
import de.edu.telran.myshop.entity.Supplier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {

    @Query("SELECT MAX (s.id) FROM Supplier s")
    Long maxSupplierId();


}
