package Valkov.Fishing_Farm_Zasmyano.service.impl.user;
import Valkov.Fishing_Farm_Zasmyano.domain.dto.user.UserChangeInfoDto;
import Valkov.Fishing_Farm_Zasmyano.domain.dto.user.UserInfoDto;
import Valkov.Fishing_Farm_Zasmyano.domain.dto.user.UserRegisterDto;
import Valkov.Fishing_Farm_Zasmyano.domain.enums.Attitude;
import Valkov.Fishing_Farm_Zasmyano.domain.model.user.User;
import Valkov.Fishing_Farm_Zasmyano.repository.UserRepository;
import Valkov.Fishing_Farm_Zasmyano.service.user.UserService;
import Valkov.Fishing_Farm_Zasmyano.service.user.UserUtilService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserUtilService userUtilService;

    @Override
    public boolean register(UserRegisterDto dto) {

        User user = this.modelMapper.map(dto, User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setAttitude(Attitude.GOOD);

        this.userRepository.save(user);
        return true;
    }

    @Override
    public String userFullName(Long userId) {
        User user = userRepository.findById(userId).get();
        return user.getFullName();
    }

    @Override
    public boolean isEmailUnique(String email) {
        return !userRepository.existsByEmail(email);
    }

    @Override
    public boolean isPhoneNumberUnique(String phoneNumber) {
        return !userRepository.existsByPhoneNumber(phoneNumber);
    }

    @Override
    public UserInfoDto getUserInfo(String email) {
        return modelMapper.map(userRepository.findByEmail(email), UserInfoDto.class);
    }

    @Override
    @Transactional
    public void updateUser(UserChangeInfoDto dto) {
        User currentUser = userUtilService.getCurrentUser();
        Long userId = currentUser.getId();
        User user = userRepository.getReferenceById(userId);

        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setPhoneNumber(dto.getPhoneNumber());

        userRepository.saveAndFlush(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
