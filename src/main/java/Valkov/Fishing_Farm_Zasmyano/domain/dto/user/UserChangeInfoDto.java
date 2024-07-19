package Valkov.Fishing_Farm_Zasmyano.domain.dto.user;

import Valkov.Fishing_Farm_Zasmyano.validation.anotation.UniquePhoneNumber;
import Valkov.Fishing_Farm_Zasmyano.validation.anotation.UniquePhoneNumberExceptCurrent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserChangeInfoDto {
    @NotNull(message = "{add.user.firstName.NotBlank}")
    @Size(min = 2, max = 20, message = "{add.user.firstName.length}")
    private String firstName;

    @NotNull(message = "{add.user.lastName.NotBlank}")
    @Size(min = 2, max = 20, message = "{add.user.lastName.length}")
    private String lastName;


    @NotNull(message ="{add.user.phoneNumber.NotBlank}" )
    @Size(min = 10 ,message = "{add.user.phoneNumber.length}")
    @UniquePhoneNumberExceptCurrent
    private String phoneNumber;

}
