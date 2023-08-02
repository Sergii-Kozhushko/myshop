/**
 * CreateProductDto.java
 *
 * @author Sergii Kozhushko, sergiikozhushko@gmail.com
 * Date of creation: 09-Jul-2023 13:13
 */

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
