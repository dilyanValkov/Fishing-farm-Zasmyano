package com.valkov.fishingfarm.service.impl;

import com.valkov.fishingfarm.domain.enums.Attitude;
import com.valkov.fishingfarm.domain.enums.Role;
import com.valkov.fishingfarm.domain.enums.Status;
import com.valkov.fishingfarm.domain.model.BungalowReservation;
import com.valkov.fishingfarm.domain.model.FishingReservation;
import com.valkov.fishingfarm.domain.model.user.User;
import com.valkov.fishingfarm.domain.model.user.UserRole;
import com.valkov.fishingfarm.repository.bungalow.BungalowBookRepository;
import com.valkov.fishingfarm.repository.fishing.FishingBookRepository;
import com.valkov.fishingfarm.repository.user.UserRepository;
import com.valkov.fishingfarm.repository.user.UserRoleRepository;
import com.valkov.fishingfarm.service.AdminService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final BungalowBookRepository bungalowBookRepository;
    private final FishingBookRepository fishingBookRepository;
    private final EmailService emailService;

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
        BungalowReservation booking = bungalowBookRepository.getReferenceById(id);
        booking.setStatus(Status.valueOf(status));
        bungalowBookRepository.save(booking);
        emailService.sendSimpleEmail(booking.getUser().getEmail(),
                booking.emailContent(),
                booking.statusMessage(booking.getStatus().toString()));
    }

    @Transactional
    @Override
    public void deleteBookBungalowById(Long id) {
        bungalowBookRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void setBookFishingStatus(Long id, String status) {
        FishingReservation booking = fishingBookRepository.getReferenceById(id);
        booking.setStatus(Status.valueOf(status));
        fishingBookRepository.save(booking);
        emailService.sendSimpleEmail(booking.getUser().getEmail(),
                booking.emailContent(),
                booking.statusMessage(booking.getStatus().toString()));
    }

    @Transactional
    @Override
    public void deleteBookFishingById(Long id) {
        fishingBookRepository.deleteById(id);
    }

}
