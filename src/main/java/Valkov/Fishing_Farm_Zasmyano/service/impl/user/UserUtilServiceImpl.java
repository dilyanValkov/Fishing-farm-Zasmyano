package Valkov.Fishing_Farm_Zasmyano.service.impl.user;

import Valkov.Fishing_Farm_Zasmyano.domain.model.user.User;
import Valkov.Fishing_Farm_Zasmyano.repository.user.UserRepository;
import Valkov.Fishing_Farm_Zasmyano.service.user.UserUtilService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserUtilServiceImpl implements UserUtilService {
    private final UserRepository userRepository;
    @Override
    public User getCurrentUser() {
        return userRepository.getByEmail(getUserDetails().getUsername());
    }

    @Override
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @Override
    public UserDetails getUserDetails() {
        return (UserDetails)getAuthentication().getPrincipal();
    }
}
