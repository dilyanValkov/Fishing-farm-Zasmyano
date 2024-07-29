package com.valkov.fishingfarm.service.user;

import com.valkov.fishingfarm.domain.model.user.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserUtilService {

    User getCurrentUser();

    Authentication getAuthentication();

    UserDetails getUserDetails();
}
