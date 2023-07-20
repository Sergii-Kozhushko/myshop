/**
 * CreateProductDto.java
 *
 * @author Sergii Kozhushko, sergiikozhushko@gmail.com
 * Date of creation: 09-Jul-2023 13:13
 */

package de.edu.telran.myshop.dto;

import lombok.Value;

import java.math.BigDecimal;
import java.util.Date;

@Value
public class CreateCustomerDto {

   String name;
   String address;
   String phone;
   String email;
   BigDecimal discountValue;
   String discountCardNumber;
   Date dateBirth;
   Date createdAt;
   Boolean acceptSMSList;

}
