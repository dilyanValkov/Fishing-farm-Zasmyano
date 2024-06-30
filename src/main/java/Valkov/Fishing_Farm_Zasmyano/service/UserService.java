package Valkov.Fishing_Farm_Zasmyano.service;
import Valkov.Fishing_Farm_Zasmyano.domain.dto.UserRegisterDto;

public interface UserService {

boolean register(UserRegisterDto userRegisterDto);

boolean passwordsMatch(UserRegisterDto userRegisterDto);

}
