package com.valkov.fishingfarm.validation.anotation;

import com.valkov.fishingfarm.validation.validator.UniquePhoneNumberExceptCurrentValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Constraint(validatedBy = UniquePhoneNumberExceptCurrentValidator.class)
public @interface UniquePhoneNumberExceptCurrent {

    String message() default "{add.user.unique.phoneNumber}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
