/**
 * Product.java
 *
 * @author Sergii Kozhushko, sergiikozhushko@gmail.com
 * Date of creation: 27-Jun-2023 13:40
 */

package de.edu.telran.myshop.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;


@Data
@Builder
@Entity
@Table(name = "sale")
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class Sale extends BaseEntity {


    private String code;

    private BigDecimal discount;

    @Column(name = "sale_condition")
    private String saleCondition;

    private BigDecimal sum;

    @EqualsAndHashCode.Exclude
    @Column(name = "is_active")
    private Boolean isActive = true;


    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;

    @OneToMany(mappedBy = "sale", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<SaleItem> items;


}
//    @OneToMany(fetch = FetchType.LAZY)
//    @JoinColumn(name = "id", referencedColumnName = "decinvoice_id")
//    private List<SaleProduct> items;