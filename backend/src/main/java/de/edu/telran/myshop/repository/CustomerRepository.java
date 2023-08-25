package de.edu.telran.myshop.repository;

import de.edu.telran.myshop.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    // empty parameters don't affect result
    @Query("SELECT c FROM Customer c where " +
            "(:name is null or :name='' or lower(c.name) like lower(concat('%', :name,'%'))) and" +
            "(:phone is null or :phone='' or lower(c.phone) like lower(concat('%', :phone,'%'))) and" +
            "(:email is null or :email='' or lower(c.email) like lower(concat('%', :email,'%')))"
    )
    Page<Customer> findByParams(@Param("name") String name,
                                @Param("email") String email,
                                @Param("phone") String phone,
                                Pageable pageable
    );

    List<Customer> findAllByOrderByNameAsc();


}
