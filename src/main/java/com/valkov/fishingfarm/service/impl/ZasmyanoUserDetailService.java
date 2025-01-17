package com.valkov.fishingfarm.service.impl;
import com.valkov.fishingfarm.domain.enums.Role;
import com.valkov.fishingfarm.domain.model.user.User;
import com.valkov.fishingfarm.domain.model.user.UserRole;
import com.valkov.fishingfarm.domain.model.user.ZasmyanoUserDetails;
import com.valkov.fishingfarm.repository.user.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class ZasmyanoUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    public ZasmyanoUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        return userRepository.findByEmail(email).map(ZasmyanoUserDetailService::map)
                .orElseThrow(
                        () -> new UsernameNotFoundException("User with email " + email + " not found!"));
    }

    private static UserDetails map(User user){

        return new ZasmyanoUserDetails(
                user.getEmail(),
                user.getPassword(),
                user.getRoles().stream().map(UserRole::getRole).map(ZasmyanoUserDetailService::map).toList(),
                user.getFirstName(),
                user.getLastName()
        );
    }

    private static GrantedAuthority map(Role role){
        return new SimpleGrantedAuthority("ROLE_" + role);
    }
}
