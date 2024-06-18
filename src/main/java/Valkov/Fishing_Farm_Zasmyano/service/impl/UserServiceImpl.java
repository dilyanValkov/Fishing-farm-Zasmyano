package Valkov.Fishing_Farm_Zasmyano.service.impl;

import Valkov.Fishing_Farm_Zasmyano.config.UserSession;
import Valkov.Fishing_Farm_Zasmyano.domain.dto.UserLoginDto;
import Valkov.Fishing_Farm_Zasmyano.domain.dto.UserRegisterDto;
import Valkov.Fishing_Farm_Zasmyano.domain.enums.Attitude;
import Valkov.Fishing_Farm_Zasmyano.domain.enums.Role;
import Valkov.Fishing_Farm_Zasmyano.domain.model.User;
import Valkov.Fishing_Farm_Zasmyano.repository.UserRepository;
import Valkov.Fishing_Farm_Zasmyano.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private  final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserSession userSession;


    public UserServiceImpl(ModelMapper modelMapper, UserRepository userRepository, PasswordEncoder passwordEncoder, UserSession userSession) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userSession = userSession;
    }

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

        if (this.userRepository.count()<3){
            mapped.setRole(Role.ADMIN);
        }else {
            mapped.setRole(Role.CUSTOMER);
        }
        this.userRepository.save(mapped);
        return true;
    }
    @Override
    public boolean passwordsMatch(UserRegisterDto userRegisterDto){
        return userRegisterDto.getPassword().equals(userRegisterDto.getConfirmPassword());
    }
    @Override
    public boolean login(UserLoginDto userLoginDto){

        Optional<User> byEmail = this.userRepository.findByEmail(userLoginDto.getEmail());

        if (byEmail.isEmpty()){
            return false;
        }
        User user = byEmail.get();

        if (!passwordEncoder.matches(userLoginDto.getPassword(), user.getPassword())){
            return false;
        }

        userSession.login(user);

        return true;
    }

    @Override
    public void logout() {
        userSession.logout();
    }

}
