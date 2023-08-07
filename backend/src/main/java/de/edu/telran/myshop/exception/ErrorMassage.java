/**
 * ErrorMassage.java
 *
 * @author Sergii Kozhushko, sergiikozhushko@gmail.com
 * Date of creation: 09-Jul-2023 10:39
 */

package de.edu.telran.myshop.exception;


public class ErrorMassage {
    public static final String PRODUCT_ID_NOT_FOUND = "Product with id does not exist";
    public static final String PRODUCT_UPDATE_ERROR = "Can not update product";
    public static final String INVALID_PRODUCT_PARAMETER = "Invalid product parameter";

    public static final String PRODUCT_UPDATE_EMPTY_ID = "Can not update product with empty Id";

    public static final String CATEGORY_ADD_WITH_ID_ERROR = "Can not add category with id, id must be empty";
    public static final String CATEGORY_NAME_EMPTY = "Category name must be not empty";
    public static final String CATEGORY_ID_EMPTY = "Category id is empty or null";
    public static final String CUSTOMER_ID_EMPTY = "Customer id is empty or null";
    public static final String CATEGORY_NOT_FOUND = "Category not found";
    public static final String CUSTOMER_NOT_FOUND = "Customer not found";
    public static final String CUSTOMER_NAME_EMPTY = "Customer name must be not empty";

    public static final String USER_EMAIL_NAME_DUPLICATE = "User already exists: change username or email";
    public static final String USER_UUID_NOT_FOUND = "User UUID not found";

    public static final String PRODUCT_NOT_FOUND_IN_DEC_INVOICE = "Product from Sales Document not found";

    public static final String PRODUCT_NOT_FOUND_IN_INC_INVOICE = "Product from Supply Document not found";
    public static final String SUPPLY_NOT_FOUND = "Supply Document nor found by id";
    public static final String INVALID_SUPPLIER_ID = "Supplier Id is empty";
    public static final String SUPPLIER_NOT_FOUND = "Supplier not found";
    public static final String SUPPLIER_NAME_EMPTY = "Supplier name is empty";


}
