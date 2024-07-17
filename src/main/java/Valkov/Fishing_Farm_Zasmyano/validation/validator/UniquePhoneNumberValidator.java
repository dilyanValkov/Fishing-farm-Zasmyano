package Valkov.Fishing_Farm_Zasmyano.validation.validator;

import Valkov.Fishing_Farm_Zasmyano.service.user.UserService;
import Valkov.Fishing_Farm_Zasmyano.validation.anotation.UniquePhoneNumber;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
public class UniquePhoneNumberValidator implements ConstraintValidator<UniquePhoneNumber, String> {

    private final UserService userService;
    @Override
    public void initialize(UniquePhoneNumber constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String phoneNumber, ConstraintValidatorContext context) {
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            return true;
        }

        return userService.isPhoneNumberUnique(phoneNumber);
    }
}
