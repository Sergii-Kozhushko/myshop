package de.edu.telran.myshop.mapper;

import de.edu.telran.myshop.dto.CreateCustomerDto;
import de.edu.telran.myshop.entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDate;

@Mapper(componentModel = "spring", imports = {LocalDate.class})

public interface CustomerMapper {

    CreateCustomerDto toCreateCustomerDto(Customer customer);
    //@Mapping(target = "createdAt", expression = "java(LocalDate.now())")
    Customer toEntity(CreateCustomerDto createCustomerDto);

}


