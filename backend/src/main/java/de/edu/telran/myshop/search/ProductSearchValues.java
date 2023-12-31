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

    private String name;
    private BigDecimal fromPrice;
    private BigDecimal toPrice;
    private Boolean active;
    private Integer pageNumber;
    private Integer pageSize;

    // sort
    private String sortColumn;
    private String sortDirection;

}
