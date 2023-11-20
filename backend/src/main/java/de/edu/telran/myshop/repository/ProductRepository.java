package de.edu.telran.myshop.repository;

import de.edu.telran.myshop.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for accessing Product entities from the database.
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    /**
     * Retrieves a page of Product entities matching the specified parameters.
     *
     * @param name     The name of the product to search for (optional).
     * @param active   The status of product's active state (optional). Not active means deleted
     * @param pageable The page information for pagination.
     * @return A page of Product entities that match the specified parameters.
     */
    @Query("SELECT p FROM Product p WHERE "
            + "(:name IS NULL OR :name='' OR lower(p.name) LIKE lower(concat('%', :name,'%'))) AND "
            + "(:active IS NULL OR p.active=:active)"
    )
    Page<Product> findByParams(@Param("name") String name,
                               @Param("active") Boolean active,
                               Pageable pageable
    );

    /**
     * Retrieves a list of active Product entities belonging to a specific category.
     * Empty parameters are not taken into account
     *
     * @param categoryId The ID of the category for which products are retrieved (optional).
     * @return A list of active Product entities that belong to the specified category.
     */
    @Query("SELECT p FROM Product p WHERE "
            + "(:category_id IS NULL OR p.category.id=:category_id) "
            + "AND p.active=true ORDER BY p.name ASC"
    )
    List<Product> findByCategoryIdAndActiveTrue(@Param("category_id") Integer categoryId);

    /**
     * Retrieves a list of active Product entities.
     *
     * @return A list of active Product entities.
     */
    @Query("SELECT p FROM Product p JOIN FETCH p.category WHERE p.active = true ORDER BY p.name")
    List<Product> findByActiveTrue();
}