package de.edu.telran.myshop.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


@Data
@Builder
@Entity
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class Product extends BaseEntity {

    @Column(name = "name", nullable = false)
    private String name;

    private BigDecimal price;

    @Column(name = "wholesale_price")
    private BigDecimal wholesalePrice;

    private Integer quantity = 0;

    @EqualsAndHashCode.Exclude
    @Column(name = "is_active")
    private Boolean active = true;


    @ManyToOne(fetch = FetchType.LAZY)
    // tell Jackson to exclude field from serialization
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private ProductCategory category;


}
