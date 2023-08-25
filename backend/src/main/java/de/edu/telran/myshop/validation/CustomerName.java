package de.edu.telran.myshop.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {CustomerNameConstraint.class})
public @interface CustomerName {
    String message() default "Invalid customer name length, must be less than 30 characters";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
