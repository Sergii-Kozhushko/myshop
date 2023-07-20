/**
 * ProductSearchValues.java
 *
 * @author Sergii Kozhushko, sergiikozhushko@gmail.com
 * Date of creation: 08-Jul-2023 12:55
 */

package de.edu.telran.myshop.search;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductSearchValues {
   // постраничность
   private String name;
   private BigDecimal fromPrice;
   private BigDecimal toPrice;
   private Boolean active;
   private Integer pageNumber;
   private Integer pageSize;

   // сортировка
   private String sortColumn;
   private String sortDirection;

}
