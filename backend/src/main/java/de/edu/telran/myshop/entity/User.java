/**
 * ProductCategory.java
 *
 * @author Sergii Kozhushko, sergiikozhushko@gmail.com
 * Date of creation: 09-Jul-2023 09:56
 */

package de.edu.telran.myshop.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

   private String id;

   private String name;

   private String email;
   private String username;
   private String password;

   private List<String> roles;


}
