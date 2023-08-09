package de.edu.telran.myshop.entity;


import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Date;


@Data
@Builder
@Entity
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class Supplier extends BaseEntity {

    @Column(nullable = false)
    private String name;

    private String address;
    private String phone;
    private String email;
    @EqualsAndHashCode.Exclude
    @Column(name = "is_active")
    private Boolean active = true;
}
