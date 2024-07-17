package Valkov.Fishing_Farm_Zasmyano.validation.anotation;
import Valkov.Fishing_Farm_Zasmyano.validation.validator.UniquePhoneNumberValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Constraint(validatedBy = UniquePhoneNumberValidator.class)
public @interface UniquePhoneNumber {

    String message() default "{add.user.unique.phoneNumber}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
