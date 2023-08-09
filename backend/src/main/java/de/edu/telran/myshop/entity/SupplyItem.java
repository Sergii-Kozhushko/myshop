package de.edu.telran.myshop.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;


@Entity
@Table(name = "supply_item")
@Builder
@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class SupplyItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    //@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    // @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) // only write the field to jason
    @JoinColumn(name = "supply_id", referencedColumnName = "id")
    private Supply supply;

    private int quantity;

    private BigDecimal price;
}
