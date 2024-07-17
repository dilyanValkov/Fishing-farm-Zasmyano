package Valkov.Fishing_Farm_Zasmyano.service.user;

import Valkov.Fishing_Farm_Zasmyano.domain.model.user.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserUtilService {

    User getCurrentUser();
    Authentication getAuthentication();

    UserDetails getUserDetails();
}
