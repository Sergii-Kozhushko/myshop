package de.edu.telran.myshop.exception;

import de.edu.telran.myshop.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleProductNotFoundException(Exception ex) {

        return ResponseEntity.status(NOT_ACCEPTABLE)
                .body(new ErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(InvalidCustomerParameterException.class)
    public ResponseEntity<ErrorResponse> handleInvalidCustomerParameterException(Exception ex) {

        return ResponseEntity.status(CONFLICT)
                .body(new ErrorResponse(ex.getMessage()));
    }


    @ExceptionHandler(InvalidProductParameterException.class)
    public ResponseEntity<ErrorResponse> handleProductIdNullException(Exception ex) {


        return ResponseEntity.status(NOT_ACCEPTABLE)
                .body(new ErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(ProductNameEmptyException.class)
    public ResponseEntity<ErrorResponse> handleProductNameEmptyException(Exception ex) {

        return ResponseEntity.status(NOT_ACCEPTABLE)
                .body(new ErrorResponse(ex.getMessage()));

    }


    @ExceptionHandler(ProductCategoryInvalidParameterException.class)
    public ResponseEntity<ErrorResponse> handleCategoryInvalidParameterException(Exception ex) {
        return ResponseEntity.status(NOT_ACCEPTABLE)
                .body(new ErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(ProductCategoryNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCategoryNotFoundException(Exception ex) {
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                .body(new ErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleUserExistsException(Exception ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(Exception ex) {
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                .body(new ErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(SupplyNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleSupplyNotFoundException(Exception ex) {
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                .body(new ErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleOtherExceptions(Exception ex) {
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                .body(new ErrorResponse(ex.getMessage()));
    }

}