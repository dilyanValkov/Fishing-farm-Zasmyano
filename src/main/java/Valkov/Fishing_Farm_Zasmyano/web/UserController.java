package Valkov.Fishing_Farm_Zasmyano.web;
import Valkov.Fishing_Farm_Zasmyano.domain.dto.user.UserRegisterDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class UserController {

    @ModelAttribute("registerData")
    public UserRegisterDto createEmptyRegisterDto() {
        return new UserRegisterDto();
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

        return "redirect:/login";
    }

    @GetMapping("/login")
    public String viewLogin(){
        return "login";
    }

    @PostMapping("login-error")
    public String loginError(Model model, @ModelAttribute ("email")String email){
        model.addAttribute("hasError",true);
        model.addAttribute("email",email);
        return "login";
    }
}

