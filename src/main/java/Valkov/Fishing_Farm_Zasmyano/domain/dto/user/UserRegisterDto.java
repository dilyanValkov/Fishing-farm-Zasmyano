package Valkov.Fishing_Farm_Zasmyano.domain.dto.user;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegisterDto {

    @NotBlank
    @Size(min = 2, max = 20)
    private String firstName;

    @NotBlank
    @Size(min = 2, max = 20)
    private String lastName;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 10)
    private String phoneNumber;

    @NotBlank
    @Size(min = 3, max = 20)
    private String password;

    @NotBlank
    @Size(min = 3, max = 20)
    private String confirmPassword;
}
