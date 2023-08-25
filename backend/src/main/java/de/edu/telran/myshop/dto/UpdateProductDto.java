package de.edu.telran.myshop.dto;

import de.edu.telran.myshop.entity.ProductCategory;
import jdk.jfr.Category;
import lombok.*;

import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProductDto {
    Long id;
    String name;
    BigDecimal price;
    BigDecimal wholesalePrice;
    Integer quantity;
    boolean active;
    ProductCategory category;
}
