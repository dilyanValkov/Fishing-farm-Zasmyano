package Valkov.Fishing_Farm_Zasmyano.service;

import Valkov.Fishing_Farm_Zasmyano.domain.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserUtilService {

    User getCurrentUser();
    Authentication getAuthentication();

    UserDetails getUserDetails();
}
