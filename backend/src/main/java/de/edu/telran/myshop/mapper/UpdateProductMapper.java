/**
 * UpdateProductMapper.java
 *
 * @author Sergii Kozhushko, sergiikozhushko@gmail.com
 * Date of creation: 28-Jul-2023 19:23
 */

package de.edu.telran.myshop.mapper;

import de.edu.telran.myshop.dto.CreateProductDto;
import de.edu.telran.myshop.dto.UpdateProductDto;
import de.edu.telran.myshop.entity.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UpdateProductMapper {

    UpdateProductDto toUpdateProductDto(Product product);

    Product toEntity(UpdateProductDto productDto);
}

