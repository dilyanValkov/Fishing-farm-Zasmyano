package Valkov.Fishing_Farm_Zasmyano.web;
import Valkov.Fishing_Farm_Zasmyano.domain.dto.user.UserChangeInfoDto;
import Valkov.Fishing_Farm_Zasmyano.domain.dto.user.UserInfoDto;
import Valkov.Fishing_Farm_Zasmyano.domain.dto.user.UserRegisterDto;
import Valkov.Fishing_Farm_Zasmyano.service.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @ModelAttribute("registerData")
    public UserRegisterDto createEmptyRegisterDto() {
        return new UserRegisterDto();
    }

    @ModelAttribute("changeUserData")
    public UserChangeInfoDto createUserChangeInfoDto(){return new UserChangeInfoDto();}

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
        userService.register(dto);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String viewLogin(){
        return "login";
    }

    @PostMapping("/login-error")
    public String loginError(Model model, @ModelAttribute ("email")String email){
        model.addAttribute("hasError",true);
        model.addAttribute("email",email);
        return "login";
    }

    @GetMapping("user/profile")
    public String userProfile(Model model, Authentication authentication){
        String email = authentication.getName();
        UserInfoDto userInfo = userService.getUserInfo(email);
        model.addAttribute("userInfo", userInfo);
        return "user-info";
    }

    @PostMapping("user/update")
    public String updateProfile(@Valid UserChangeInfoDto user,
                                Model model, BindingResult bindingResult,
                                RedirectAttributes redirectAttributes){


        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("changeUserData", user);
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.changeUserData", bindingResult);
            return "redirect:/user/profile";
        }

        redirectAttributes.addFlashAttribute("success", true);
        userService.updateUser(user);
        return "redirect:/user/profile";
    }

    @DeleteMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/";
    }


}

