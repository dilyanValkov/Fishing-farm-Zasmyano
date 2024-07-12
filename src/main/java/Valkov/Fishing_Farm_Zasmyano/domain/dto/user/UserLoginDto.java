package Valkov.Fishing_Farm_Zasmyano.domain.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLoginDto {
    @NotBlank
    @Email
    @Size(min = 3, max = 30)
    private String email;

    @Size(min = 3, max = 20)
    private String password;
}
