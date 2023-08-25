package de.telran.myshop.bff.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// криетрии поиска
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class SearchValues {

    private String searchText; // поиск данных по какому-либо значению

}
