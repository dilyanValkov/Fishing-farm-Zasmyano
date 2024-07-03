package Valkov.Fishing_Farm_Zasmyano.service.impl;
import Valkov.Fishing_Farm_Zasmyano.domain.dto.UserRegisterDto;
import Valkov.Fishing_Farm_Zasmyano.domain.enums.Attitude;
import Valkov.Fishing_Farm_Zasmyano.domain.model.User;
import Valkov.Fishing_Farm_Zasmyano.repository.UserRepository;
import Valkov.Fishing_Farm_Zasmyano.service.UserService;
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
    public boolean register(UserRegisterDto userRegisterDto) {

        boolean isEmailOrPhoneTaken= this.userRepository.existsByEmailOrPhoneNumber(
                userRegisterDto.getEmail(), userRegisterDto.getPhoneNumber());

        if (isEmailOrPhoneTaken){
            return false;
        }

        User mapped = this.modelMapper.map(userRegisterDto, User.class);
        mapped.setPassword(passwordEncoder.encode(mapped.getPassword()));
        mapped.setAttitude(Attitude.GOOD);


        this.userRepository.save(mapped);
        return true;
    }
    @Override
    public boolean passwordsMatch(UserRegisterDto userRegisterDto){
        return userRegisterDto.getPassword().equals(userRegisterDto.getConfirmPassword());
    }
}
