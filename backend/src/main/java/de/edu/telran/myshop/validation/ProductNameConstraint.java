package de.edu.telran.myshop.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class ProductNameConstraint implements ConstraintValidator<ProductName, String> {

    private static final String PRODUCT_NAME_PATTERN = "\\w{10}";

    @Override
    public void initialize(ProductName constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return Optional.ofNullable(value)
                .filter(s -> !s.isBlank())
                .map(s -> s.matches(PRODUCT_NAME_PATTERN))
                .orElse(false);
    }
}
