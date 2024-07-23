package Valkov.Fishing_Farm_Zasmyano.service.impl;

import Valkov.Fishing_Farm_Zasmyano.domain.enums.Attitude;
import Valkov.Fishing_Farm_Zasmyano.domain.enums.Role;
import Valkov.Fishing_Farm_Zasmyano.domain.enums.Status;
import Valkov.Fishing_Farm_Zasmyano.domain.model.BungalowReservation;
import Valkov.Fishing_Farm_Zasmyano.domain.model.user.User;
import Valkov.Fishing_Farm_Zasmyano.domain.model.user.UserRole;
import Valkov.Fishing_Farm_Zasmyano.repository.bungalow.BungalowBookingRepository;
import Valkov.Fishing_Farm_Zasmyano.repository.user.UserRepository;
import Valkov.Fishing_Farm_Zasmyano.repository.user.UserRoleRepository;
import Valkov.Fishing_Farm_Zasmyano.service.AdminService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final BungalowBookingRepository bungalowBookingRepository;

    @Transactional
    @Override
    public void setUserRole(Long userId, String role) {
        User user = userRepository.getReferenceById(userId);

        switch (role){
            case "ADMIN":
                List<UserRole> allRoles = userRoleRepository.findAll();
                user.setRoles(allRoles);
                userRepository.save(user);
                break;
            case "USER":
                user.getRoles().removeIf(ur -> ur.getRole().equals(Role.ADMIN));
                userRepository.save(user);
                break;
        }
    }

    @Transactional
    @Override
    public void setUserAttitude(Long userId, String attitude) {
        User user = userRepository.getReferenceById(userId);
        user.setAttitude(Attitude.valueOf(attitude));
        userRepository.save(user);
    }

    @Transactional
    @Override
    public void setBookBungalowStatus(Long id, String status) {
        BungalowReservation booking = bungalowBookingRepository.getReferenceById(id);
        booking.setStatus(Status.valueOf(status));
        bungalowBookingRepository.save(booking);
    }
    @Transactional
    @Override
    public void deleteBookBungalowById(Long id) {
        bungalowBookingRepository.deleteById(id);
    }

    @Override
    public void deleteBookFishingById(Long id) {

    }
}
