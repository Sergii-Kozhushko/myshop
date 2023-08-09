package de.edu.telran.myshop.exception;

public class ProductCategoryNotFoundException extends RuntimeException {

    public ProductCategoryNotFoundException(String message) {
        super(message);
    }
}
