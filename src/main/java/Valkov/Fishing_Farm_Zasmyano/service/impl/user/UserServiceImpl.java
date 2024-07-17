package Valkov.Fishing_Farm_Zasmyano.service.impl.user;
import Valkov.Fishing_Farm_Zasmyano.domain.dto.user.UserRegisterDto;
import Valkov.Fishing_Farm_Zasmyano.domain.enums.Attitude;
import Valkov.Fishing_Farm_Zasmyano.domain.model.user.User;
import Valkov.Fishing_Farm_Zasmyano.repository.UserRepository;
import Valkov.Fishing_Farm_Zasmyano.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

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

}
