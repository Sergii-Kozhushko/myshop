/**
 * ResponseExceptionHandler.java
 *
 * @author Sergii Kozhushko, sergiikozhushko@gmail.com
 * Date of creation: 09-Jul-2023 11:01
 */

package de.edu.telran.myshop.exception;

import de.edu.telran.myshop.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleProductNotFoundException(Exception ex) {

        return ResponseEntity.status(BAD_REQUEST)
                .body(new ErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(InvalidProductParameterException.class)
    public ResponseEntity<ErrorResponse> handleProductIDNullException(Exception ex) {


        return ResponseEntity.status(BAD_REQUEST)
                .body(new ErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(ProductNameEmptyException.class)
    public ResponseEntity<ErrorResponse> handleProductNameEmptyException(Exception ex) {

        return ResponseEntity.status(BAD_REQUEST)
                .body(new ErrorResponse(ex.getMessage()));

    }

//    @ExceptionHandler(CategoryIdMustBeEmptyException.class)
//    public ResponseEntity<ErrorResponse> handleCategoryIdMustBeEmptyException(Exception ex) {
//        ErrorResponse body = new ErrorResponse(
//                ex.getMessage(),
//                ErrorCode.CATEGORY_ID_NOT_EMPTY);
//        return new ResponseEntity<>(body, BAD_REQUEST);
//    }

    @ExceptionHandler(ProductCategoryInvalidParameterException.class)
    public ResponseEntity<ErrorResponse> handleCategoryInvalidParameterException(Exception ex) {
        return ResponseEntity.status(BAD_REQUEST)
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
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(ex.getMessage()));
    }



}
