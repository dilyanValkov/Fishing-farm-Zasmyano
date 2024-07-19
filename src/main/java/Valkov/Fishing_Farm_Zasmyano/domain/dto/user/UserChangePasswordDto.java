package Valkov.Fishing_Farm_Zasmyano.domain.dto.user;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class UserChangePasswordDto {

    @NotNull(message = "{add.user.password.length}")
    @Length(min = 3, max = 20 ,message = "{add.user.password.length}")
    private String oldPassword;

    @NotNull(message = "{add.user.password.length}")
    @Length(min = 3, max = 20 ,message = "{add.user.password.length}")
    private String newPassword;

    private String confirmPassword;
}
