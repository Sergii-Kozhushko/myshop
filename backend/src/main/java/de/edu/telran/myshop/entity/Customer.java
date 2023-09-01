package de.edu.telran.myshop.entity;


import de.edu.telran.myshop.validation.CustomerName;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;


@Data
@Builder
@Entity
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class Customer extends BaseEntity {

    @Column(nullable = false)
    // @CustomerName // custom validation
    @Pattern(regexp = "^[A-Za-z\\s-]*$", message = "Customer name must contain only english letters, spaces or '-'.")
    @Size(min = 3, max = 50, message = "Invalid name length: must be between 3 and 50 chars")
    private String name;
    @Size(min = 0, max = 150, message = "Address length must be less than 150 chars")
    private String address;
    private String phone;

    private String email;

    @Column(name = "discount")
    private Integer discountValue;

    @Column(name = "discount_card")
    private String discountCardNumber;
    @Column(name = "date_of_birth")
    private Date dateBirth;

    @EqualsAndHashCode.Exclude
    @Column(name = "accept_sms_list")
    Boolean acceptSMSList = false;
}
