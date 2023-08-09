package de.edu.telran.myshop.dto;

import de.edu.telran.myshop.entity.ProductCategory;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.math.BigDecimal;

@Value
public class CreateProductDto {
    String name;
    BigDecimal price;
    BigDecimal wholesalePrice;
    int quantity;
    ProductCategory category;
    boolean active = true;

}
