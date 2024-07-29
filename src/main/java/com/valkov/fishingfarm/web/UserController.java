package com.valkov.fishingfarm.web;
import com.valkov.fishingfarm.domain.dto.user.UserChangeInfoDto;
import com.valkov.fishingfarm.domain.dto.user.UserChangePasswordDto;
import com.valkov.fishingfarm.domain.dto.user.UserInfoDto;
import com.valkov.fishingfarm.domain.dto.user.UserRegisterDto;
import com.valkov.fishingfarm.service.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
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

    @ModelAttribute("passwordData")
    public UserChangePasswordDto createUserChangePasswordDto(){return new UserChangePasswordDto();}

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
                                BindingResult bindingResult,
                                RedirectAttributes redirectAttributes){

            if (bindingResult.hasErrors()) {
                redirectAttributes.addFlashAttribute("changeUserData", user);
                redirectAttributes.addFlashAttribute("hasError", true);
                redirectAttributes.addFlashAttribute(
                        "org.springframework.validation.BindingResult.changeUserData", bindingResult);
                return "redirect:/user/profile";
            }

            redirectAttributes.addFlashAttribute("success", true);
            userService.updateUser(user);

            return "redirect:/user/profile";
    }

    @PostMapping("user/change-password")
    public String changePassword(@Valid UserChangePasswordDto dto,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes){

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("passwordData", dto);
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.passwordData", bindingResult);
            return "redirect:/user/profile";
        }
        if (!userService.passwordMatches(dto.getOldPassword(), dto.getNewPassword(),dto.getConfirmPassword())) {
            redirectAttributes.addFlashAttribute("passwordMismatch", true);
        }else {
            userService.changePassword(dto);
            redirectAttributes.addFlashAttribute("passwordMatch", true);
        }

        return "redirect:/user/profile";

    }

    @DeleteMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id, HttpServletRequest request) {

        userService.deleteUser(id);
        request.getSession().invalidate();

        return "redirect:/";
    }

}

