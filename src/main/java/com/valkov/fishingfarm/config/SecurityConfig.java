package com.valkov.fishingfarm.config;

import com.valkov.fishingfarm.repository.user.UserRepository;
import com.valkov.fishingfarm.service.impl.ZasmyanoUserDetailService;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .authorizeHttpRequests(
                        authorizeRequests ->
                                authorizeRequests
                                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                                .requestMatchers("/","/about","/login","/register",
                                        "/contact","/review/all-review","/login-error").permitAll()
                                .anyRequest()
                                .authenticated()
                )
                .formLogin(formLogin->
                        formLogin.loginPage("/login")
                                .usernameParameter("email")
                                .passwordParameter("password")
                                .defaultSuccessUrl("/home", true)
                                .failureForwardUrl("/login-error")

                )
                .logout(logout->
                        logout.logoutUrl("/logout")
                              .logoutSuccessUrl("/")
                              .invalidateHttpSession(true)

                )
                .build();
    }

    @Bean
    public ZasmyanoUserDetailService userDetailService (UserRepository userRepository){
        return new ZasmyanoUserDetailService(userRepository);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return Pbkdf2PasswordEncoder.defaultsForSpringSecurity_v5_8();
    }

}
