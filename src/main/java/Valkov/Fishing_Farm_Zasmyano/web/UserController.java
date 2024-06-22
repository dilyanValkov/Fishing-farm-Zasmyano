package Valkov.Fishing_Farm_Zasmyano.web;

import Valkov.Fishing_Farm_Zasmyano.config.UserSession;
import Valkov.Fishing_Farm_Zasmyano.domain.dto.UserLoginDto;
import Valkov.Fishing_Farm_Zasmyano.domain.dto.UserRegisterDto;
import Valkov.Fishing_Farm_Zasmyano.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserController {
    private final UserService userService;
    private final UserSession userSession;

    public UserController(UserService userService, UserSession userSession) {
        this.userService = userService;
        this.userSession = userSession;
    }
    @ModelAttribute("registerData")
    public UserRegisterDto createEmptyRegisterDto() {
        return new UserRegisterDto();
    }

    @ModelAttribute("loginData")
    public UserLoginDto createEmptyLoginDto() {
        return new UserLoginDto();
    }

    @GetMapping("/register")
    public String viewRegister(){
        return "register";
    }
    @PostMapping("/register")
    public String doRegister(@Valid UserRegisterDto dto,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes){

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("registerData", dto);
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.registerData", bindingResult);
        return "redirect:/register";
        }

        if (!userService.passwordsMatch(dto)){
            redirectAttributes.addFlashAttribute("registerData",dto);
            redirectAttributes.addFlashAttribute(
                    "passwordsMatch", true);
            return "redirect:/register";
        }

        if (!userService.register(dto)) {
            redirectAttributes.addFlashAttribute("registerData",dto);
            redirectAttributes.addFlashAttribute(
                    "userEmailOrPhoneTaken", true);
            return "redirect:/register";
        }
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String viewLogin(){
        return "login";
    }

    @PostMapping("/login")
    public String doLogin(@Valid UserLoginDto dto,
                          BindingResult bindingResult,
                          RedirectAttributes redirectAttributes){

        if (bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("loginData",dto);
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.loginData", bindingResult);
            return "redirect:/login";
        }

        boolean success = userService.login(dto);

        if (!success){
            redirectAttributes.addFlashAttribute("loginData",dto);
            redirectAttributes.addFlashAttribute(
                    "userPassMismatch", true);
            return "redirect:/login";
        }
            return "redirect:/";

    }

    @GetMapping("/logout")
    public String logout() {
        if (!userSession.isUserLoggedIn()) {
            return "redirect:/";
        }
        userSession.logout();
        return "redirect:/";
    }
}

