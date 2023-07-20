/**
 * ProductNotFoundException.java
 *
 * @author Sergii Kozhushko, sergiikozhushko@gmail.com
 * Date of creation: 09-Jul-2023 10:37
 */

package de.edu.telran.myshop.exception;

public class UserAlreadyExistsException extends RuntimeException {

   public UserAlreadyExistsException(String message) {
      super(message);
   }
}
