package com.valkov.fishingfarm.domain.dto.user;
import com.valkov.fishingfarm.validation.anotation.UniqueEmail;
import com.valkov.fishingfarm.validation.anotation.UniquePhoneNumber;
import com.valkov.fishingfarm.validation.anotation.ValidatePasswords;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;


@Getter
@Setter
@ValidatePasswords
public class UserRegisterDto {
    @NotNull(message = "{add.user.firstName.NotBlank}")
    @Size(min = 2, max = 20, message = "{add.user.firstName.length}")
    private String firstName;

    @NotNull(message = "{add.user.lastName.NotBlank}")
    @Size(min = 2, max = 20, message = "{add.user.lastName.length}")
    private String lastName;

    @NotBlank(message = "{add.user.email.NotBlank}")
    @Email(message = "{add.user.email.NotBlank}")
    @UniqueEmail
    private String email;

    @NotNull(message ="{add.user.phoneNumber.NotBlank}" )
    @Size(min = 10 ,message = "{add.user.phoneNumber.length}")
    @UniquePhoneNumber
    private String phoneNumber;

    @NotNull(message = "{add.user.password.length}")
    @Length(min = 3, max = 20 ,message = "{add.user.password.length}")
    private String password;

    private String confirmPassword;
}
