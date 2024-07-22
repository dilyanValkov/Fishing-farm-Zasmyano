package Valkov.Fishing_Farm_Zasmyano.web;

import Valkov.Fishing_Farm_Zasmyano.domain.dto.user.UserInfoAdminDto;
import Valkov.Fishing_Farm_Zasmyano.service.AdminService;
import Valkov.Fishing_Farm_Zasmyano.service.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final AdminService adminService;


    @ModelAttribute("user")
    public UserInfoAdminDto createDto(){return new UserInfoAdminDto();}
    @GetMapping()
    public String adminPage(Model model){
        List<UserInfoAdminDto> users = userService.findAll();
        model.addAttribute("users", users);
        return "admin";
    }

    @PostMapping("/updateRole")
    public String updateRole(UserInfoAdminDto dto){
        adminService.setUserRole(dto.getId(),dto.getRole());
        return "redirect:/admin";
    }

    @PostMapping("/updateAttitude")
    public String updateAttitude(UserInfoAdminDto dto){
        adminService.setUserAttitude(dto.getId(),dto.getAttitude().toString());
        return "redirect:/admin";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}
