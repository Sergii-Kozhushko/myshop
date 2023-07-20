/**
 * UserDTO.java
 *
 * @author Sergii Kozhushko, sergiikozhushko@gmail.com
 * Date of creation: 15-Jul-2023 07:37
 */

package de.edu.telran.myshop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
   private String id;
   private String email;
   private String username;
   private String password;

}
