package de.edu.telran.myshop.search;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerSearchValues {
    private String name;
    private String phone;
    private String email;

    // page layout and sort
    private Integer pageNumber;
    private Integer pageSize;
    private String sortColumn;
    private String sortDirection;

}
