package de.telran.myshop.bff.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DataResult {

    private String data; // User's business data

    // We use any fields depending on the task

    // It's not necessary to add the entire user profile data here to avoid overloading the request
    // Only minimal data should be included
    // If you need to retrieve complete user data (profile), it can be done through a separate method

}