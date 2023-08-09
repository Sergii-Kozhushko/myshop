package de.edu.telran.myshop.dto;

import de.edu.telran.myshop.entity.ProductCategory;
import jdk.jfr.Category;
import lombok.Value;

import java.math.BigDecimal;

@Value
public class UpdateProductDto {
    Long id;
    String name;
    BigDecimal price;
    BigDecimal wholesalePrice;
    Integer quantity;
    boolean active;
    ProductCategory category;
}
