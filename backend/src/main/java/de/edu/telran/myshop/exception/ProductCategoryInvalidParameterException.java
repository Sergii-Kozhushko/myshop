/**
 * CategoryIdMustBeEmptyException.java
 *
 * @author Sergii Kozhushko, sergiikozhushko@gmail.com
 * Date of creation: 09-Jul-2023 12:30
 */

package de.edu.telran.myshop.exception;

public class ProductCategoryInvalidParameterException extends RuntimeException{
   public ProductCategoryInvalidParameterException(String message) {
      super(message);
   }
}
