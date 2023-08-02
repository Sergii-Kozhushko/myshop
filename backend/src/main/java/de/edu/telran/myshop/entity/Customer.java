/**
 * Product.java
 *
 * @author Sergii Kozhushko, sergiikozhushko@gmail.com
 * Date of creation: 27-Jun-2023 13:40
 */

package de.edu.telran.myshop.entity;


import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


@Data
@Builder
@Entity
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class Customer extends BaseEntity {

    @Column(nullable = false)
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
