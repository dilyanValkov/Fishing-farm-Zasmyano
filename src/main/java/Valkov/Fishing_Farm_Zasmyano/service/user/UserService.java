package Valkov.Fishing_Farm_Zasmyano.service.user;
import Valkov.Fishing_Farm_Zasmyano.domain.dto.user.UserRegisterDto;

public interface UserService {

boolean register(UserRegisterDto userRegisterDto);


String userFullName(Long userId);

boolean isEmailUnique(String email);
boolean isPhoneNumberUnique(String phoneNumber);
}
