/**
 * Product.java
 *
 * @author Sergii Kozhushko, sergiikozhushko@gmail.com
 * Date of creation: 27-Jun-2023 13:40
 */

package de.edu.telran.myshop.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;


@Data
@Builder
@Entity
@Table(name = "dec_invoice")
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class DecInvoice extends BaseEntity {


    private String code;

    private BigDecimal discount;

    @Column(name = "sale_condition")
    private String saleCondition;

    @EqualsAndHashCode.Exclude
    @Column(name = "is_active")
    private Boolean isActive = true;


    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;
}
