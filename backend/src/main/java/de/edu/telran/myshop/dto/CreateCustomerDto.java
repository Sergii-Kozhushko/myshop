package de.edu.telran.myshop.dto;

import lombok.*;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateCustomerDto {

    String name;
    String address;
    String phone;
    String email;
    Integer discountValue;
    String discountCardNumber;
    Date dateBirth;
    Boolean acceptSmsList;
}