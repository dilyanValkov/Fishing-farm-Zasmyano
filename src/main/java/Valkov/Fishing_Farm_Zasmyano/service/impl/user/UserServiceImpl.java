package Valkov.Fishing_Farm_Zasmyano.service.impl.user;
import Valkov.Fishing_Farm_Zasmyano.domain.dto.bungalow.BookInfoBungalowDto;
import Valkov.Fishing_Farm_Zasmyano.domain.dto.fishing.BookInfoFishingDto;
import Valkov.Fishing_Farm_Zasmyano.domain.dto.user.*;
import Valkov.Fishing_Farm_Zasmyano.domain.enums.Attitude;
import Valkov.Fishing_Farm_Zasmyano.domain.enums.Role;
import Valkov.Fishing_Farm_Zasmyano.domain.model.user.User;
import Valkov.Fishing_Farm_Zasmyano.domain.model.user.UserRole;
import Valkov.Fishing_Farm_Zasmyano.repository.UserRepository;
import Valkov.Fishing_Farm_Zasmyano.service.ReviewService;
import Valkov.Fishing_Farm_Zasmyano.service.book.BungalowBookService;
import Valkov.Fishing_Farm_Zasmyano.service.book.FishingBookService;
import Valkov.Fishing_Farm_Zasmyano.service.user.UserService;
import Valkov.Fishing_Farm_Zasmyano.service.user.UserUtilService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service

public class UserServiceImpl implements UserService {
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserUtilService userUtilService;
    private final FishingBookService fishingBookService;
    private final BungalowBookService bungalowBookService;
    private final ReviewService reviewService;

    public UserServiceImpl(ModelMapper modelMapper,
                           UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           UserUtilService userUtilService,
                           FishingBookService fishingBookService,
                           BungalowBookService bungalowBookService,
                           @Lazy ReviewService reviewService) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userUtilService = userUtilService;
        this.fishingBookService = fishingBookService;
        this.bungalowBookService = bungalowBookService;
        this.reviewService = reviewService;
    }

    @Override
    public void register(UserRegisterDto dto) {
        //TODO role menage
       // List<UserRole> roles = new ArrayList<>();
      //  roles.add(new UserRole(Role.USER));

        User user = this.modelMapper.map(dto, User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setAttitude(Attitude.GOOD);
      //  user.getRoles().add();
        this.userRepository.save(user);
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
        User user = getCurrentUser();

        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setPhoneNumber(dto.getPhoneNumber());

        userRepository.saveAndFlush(user);
    }
    @Transactional
    @Override
    @Lazy
    public void deleteUser(Long id) {
        fishingBookService.deleteReservations(id);
        bungalowBookService.deleteReservations(id);
        reviewService.deleteAllReviews(id);
        userRepository.deleteById(id);
    }

    @Override
    @Transactional
    public boolean isPhoneNumberUniqueExceptCurrent(String phoneNumber) {
        User user = getCurrentUser();

        if (user.getPhoneNumber().equals(phoneNumber)){
            return true;
        }

        return !userRepository.existsByPhoneNumber(phoneNumber);
    }

    @Override
    @Transactional
    public void changePassword(UserChangePasswordDto dto) {
        User user = getCurrentUser();

        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));

        userRepository.saveAndFlush(user);
    }

    @Override
    public boolean passwordMatches(String oldPassword, String newPassword, String confirmPassword) {
        User user = userUtilService.getCurrentUser();
        String encodedPassword = user.getPassword();

        return newPassword.equals(confirmPassword) && passwordEncoder.matches(oldPassword, encodedPassword);
    }

    @Override
    public List<UserInfoAdminDto> findAll() {
        List<UserInfoAdminDto> dtos = new ArrayList<>();
        List<BookInfoFishingDto> fishingBookings = fishingBookService.getAllBookings();
        List<BookInfoBungalowDto> bungalowBookings = bungalowBookService.getAllBookings();
        List<User> users = userRepository.findAll();

        for (User user : users) {
            UserInfoAdminDto dto = new UserInfoAdminDto();
            
            dto.setId(user.getId());
            dto.setFirstName(user.getFirstName());
            dto.setLastName(user.getLastName());
            dto.setEmail(user.getEmail());
            dto.setAttitude(user.getAttitude());
            dto.setPhoneNumber(user.getPhoneNumber());
            dto.setBungalowReservations(bungalowBookings);
            dto.setFishingReservations(fishingBookings);
            dto.setRole(user.getRoles().getFirst().getRole().toString());
            dtos.add(dto);
        }

        return dtos;
    }


    private User getCurrentUser(){
        User currentUser = userUtilService.getCurrentUser();
        Long userId = currentUser.getId();
        return userRepository.getReferenceById(userId);
    }
}
