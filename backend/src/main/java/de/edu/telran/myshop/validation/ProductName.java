package de.edu.telran.myshop.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {ProductNameConstraint.class})
public @interface ProductName {
    String message() default "Invalid product name length, must be less than 10 character ";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
