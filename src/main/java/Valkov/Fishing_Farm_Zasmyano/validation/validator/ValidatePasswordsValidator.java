package Valkov.Fishing_Farm_Zasmyano.validation.validator;

import Valkov.Fishing_Farm_Zasmyano.domain.dto.user.UserRegisterDto;
import Valkov.Fishing_Farm_Zasmyano.validation.anotation.ValidatePasswords;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;

public class ValidatePasswordsValidator implements ConstraintValidator<ValidatePasswords, UserRegisterDto> {
    private String message;
    @Override
    public void initialize(ValidatePasswords constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(UserRegisterDto dto, ConstraintValidatorContext context) {

        if (dto.getPassword() == null || dto.getConfirmPassword() == null) {
            return true;
        }
        boolean isValid = dto.getPassword().equals(dto.getConfirmPassword());

        if (!isValid) {
            context.unwrap(HibernateConstraintValidatorContext.class)
                    .buildConstraintViolationWithTemplate(message)
                    .addPropertyNode("confirmPassword")
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
        }

        return isValid;
    }
}
