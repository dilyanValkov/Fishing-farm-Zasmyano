package Valkov.Fishing_Farm_Zasmyano.web;

import Valkov.Fishing_Farm_Zasmyano.domain.dto.user.UserInfoAdminDto;
import Valkov.Fishing_Farm_Zasmyano.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;


    @ModelAttribute("user")
    public UserInfoAdminDto createDto(){return new UserInfoAdminDto();}
    @GetMapping()
    public String adminPage(Model model){
        List<UserInfoAdminDto> users = userService.findAll();
        model.addAttribute("users", users);
        return "admin";
    }

}
