package com.valkov.fishingfarm.validation.validator;

import com.valkov.fishingfarm.service.user.UserService;
import com.valkov.fishingfarm.validation.anotation.UniquePhoneNumberExceptCurrent;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UniquePhoneNumberExceptCurrentValidator implements ConstraintValidator<UniquePhoneNumberExceptCurrent, String> {
    private final UserService userService;
    @Override
    public void initialize(UniquePhoneNumberExceptCurrent constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String phoneNumber, ConstraintValidatorContext context) {
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            return true;
        }

        return userService.isPhoneNumberUniqueExceptCurrent(phoneNumber);
    }
}
