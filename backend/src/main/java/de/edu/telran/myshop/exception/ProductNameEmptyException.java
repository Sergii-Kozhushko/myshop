package de.edu.telran.myshop.exception;

public class ProductNameEmptyException extends RuntimeException {

    public ProductNameEmptyException(String message) {
        super(message);
    }
}
