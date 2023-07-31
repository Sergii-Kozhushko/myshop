/**
 * ProductRepository.java
 *
 * @author Sergii Kozhushko, sergiikozhushko@gmail.com
 * Date of creation: 27-Jun-2023 18:44
 */

package de.edu.telran.myshop.repository;

import de.edu.telran.myshop.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT p FROM Product p where " +
            "(:name is null or :name='' or lower(p.name) like lower(concat('%', :name,'%'))) and" +
            "(:active is null or p.active=:active)"   // учитываем, что параметр может быть null или пустым

    )
        // искать по всем переданным параметрам (пустые параметры учитываться не будут)
    Page<Product> findByParams(@Param("name") String name,
                               @Param("active") Boolean active,
                               Pageable pageable
    );

    @Query("SELECT p FROM Product p where " +
            "(:category_id is null or p.category.id=:category_id)" +
            " and :is_Active=true"
    )
    List<Product> findByCategory(@Param("category_id") Integer category_id);

    @Transactional
    @Query("SELECT p FROM Product p JOIN FETCH p.category WHERE p.active = true")
    List<Product> findByActiveTrue();

    List<Product> findByCategoryAndActiveTrue(Integer id);


}
