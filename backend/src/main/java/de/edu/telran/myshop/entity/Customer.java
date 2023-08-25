package de.edu.telran.myshop.entity;


import de.edu.telran.myshop.validation.CustomerName;
import lombok.*;

import javax.persistence.*;
import java.util.Date;


@Data
@Builder
@Entity
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class Customer extends BaseEntity {

    @Column(nullable = false)
    @CustomerName // custom validation
    private String name;

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
