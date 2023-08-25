package de.edu.telran.myshop.dto;

import de.edu.telran.myshop.entity.ProductCategory;
import lombok.*;

import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateProductDto {
    String name;
    BigDecimal price;
    BigDecimal wholesalePrice;
    int quantity;
    ProductCategory category;
    boolean active = true;
}
