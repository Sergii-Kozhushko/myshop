package de.telran.myshop.bff.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpMethod;

/**
 * A universal container object that BFF receives from the client application (Angular, React).
 * The fields contain information about which address to invoke, which HTTP method to use, and what body to include.
 * In essence, BFF utilizes this data to make a request to a specific Resource Server (from the url field)
 * and also adds the access token to the request.
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Operation {

    private HttpMethod httpMethod; // The HTTP method to use.
    private String url; // The address to be called.
    private Object body; // The request body to include.

}

