package de.edu.telran.myshop.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CustomerNameConstraint implements ConstraintValidator<CustomerName, String> {

    private static final String CUSTOMER_NAME_PATTERN = "^[A-Za-z\\- ]{1,30}$";

    @Override
    public void initialize(CustomerName constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        return value.matches(CUSTOMER_NAME_PATTERN);

    }
}
