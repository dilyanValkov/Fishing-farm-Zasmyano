package Valkov.Fishing_Farm_Zasmyano.service.user;
import Valkov.Fishing_Farm_Zasmyano.domain.dto.user.*;

import java.util.List;

public interface UserService {

void register(UserRegisterDto userRegisterDto);

String userFullName(Long userId);

boolean isEmailUnique(String email);

boolean isPhoneNumberUnique(String phoneNumber);

UserInfoDto getUserInfo(String email);

void updateUser(UserChangeInfoDto user);

void deleteUser(Long id);

boolean isPhoneNumberUniqueExceptCurrent(String phoneNumber);

void changePassword(UserChangePasswordDto dto);

boolean passwordMatches(String oldPassword, String newPassword, String confirmPassword );

List<UserInfoAdminDto> findAll();

String getUserEmail(Long userId);
}
