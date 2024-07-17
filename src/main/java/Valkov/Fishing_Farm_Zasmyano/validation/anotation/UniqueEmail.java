package Valkov.Fishing_Farm_Zasmyano.validation.anotation;

import Valkov.Fishing_Farm_Zasmyano.validation.validator.UniqueEmailValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Constraint(validatedBy = UniqueEmailValidator.class)
public @interface UniqueEmail {
    String message() default "{add.user.unique.email}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}