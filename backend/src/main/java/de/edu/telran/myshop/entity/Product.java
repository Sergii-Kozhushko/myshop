/**
 * Product.java
 *
 * @author Sergii Kozhushko, sergiikozhushko@gmail.com
 * Date of creation: 27-Jun-2023 13:40
 */

package de.edu.telran.myshop.entity;


import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;



@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class Product extends BaseEntity {


    @Column(name = "name", nullable = false)
    private String name;

    private BigDecimal price;

    @Column(name="wholesale_price")
    private BigDecimal wholesalePrice;

    private Integer quantity;

    @EqualsAndHashCode.Exclude
    @Column(name="is_active")
    private Boolean active;



    @ManyToOne
    @JoinColumn(name="category_id", referencedColumnName = "id")
    private ProductCategory category;


}
