package Valkov.Fishing_Farm_Zasmyano.domain.dto.user;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfoDto {
    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private String password;
}
