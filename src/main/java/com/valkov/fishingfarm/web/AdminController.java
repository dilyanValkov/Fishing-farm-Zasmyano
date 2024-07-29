package com.valkov.fishingfarm.web;

import com.valkov.fishingfarm.domain.dto.bungalow.BookInfoBungalowDto;
import com.valkov.fishingfarm.domain.dto.fishing.BookInfoFishingDto;
import com.valkov.fishingfarm.domain.dto.user.UserInfoAdminDto;
import com.valkov.fishingfarm.service.AdminService;
import com.valkov.fishingfarm.service.book.BungalowBookService;
import com.valkov.fishingfarm.service.book.FishingBookService;
import com.valkov.fishingfarm.service.user.UserService;
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
    private final BungalowBookService bungalowBookService;
    private final FishingBookService fishingBookService;


    @ModelAttribute("user")
    public UserInfoAdminDto createUserDto(){return new UserInfoAdminDto();}

    @ModelAttribute("bungalowBooking")
    public BookInfoBungalowDto createBungalowDto(){return new BookInfoBungalowDto();}

    @ModelAttribute("fishingBook")
    public BookInfoFishingDto createFishingDto(){return new BookInfoFishingDto();}

    @GetMapping("/user")
    public String adminUserPage(Model model){
        List<UserInfoAdminDto> users = userService.findAll().reversed();
        model.addAttribute("users", users);
        return "admin-user";
    }

    @PostMapping("/user/updateRole")
    public String updateUserRole(UserInfoAdminDto dto){
        adminService.setUserRole(dto.getId(),dto.getRole());
        return "redirect:/admin/user";
    }

    @PostMapping("/user/updateAttitude")
    public String updateUserAttitude(UserInfoAdminDto dto){
        adminService.setUserAttitude(dto.getId(),dto.getAttitude().toString());
        return "redirect:/admin/user";
    }

    @DeleteMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin/user";
    }

    @GetMapping("/book/bungalow")
    public String viewBungalowBooking(Model model){
        List<BookInfoBungalowDto> bookings = bungalowBookService.getAllBookings().reversed();
        model.addAttribute("bungalowBookings", bookings);
        return "admin-book-bungalow";
    }

    @PostMapping("/book/bungalow/updateStatus")
    public String updateBookBungalowStatus(BookInfoBungalowDto dto){
        adminService.setBookBungalowStatus(dto.getReservationNumber(), dto.getStatus().toString());
        return "redirect:/admin/book/bungalow";
    }

    @DeleteMapping("/book/bungalow/delete/{id}")
    public String deleteBookBungalow(@PathVariable("id") Long id) {
        adminService.deleteBookBungalowById(id);
        return "redirect:/admin/book/bungalow";
    }


    @GetMapping("/book/fishing")
    public String viewFishingBooking(Model model){
        List<BookInfoFishingDto> bookings = fishingBookService.getAllBookings().reversed();
        model.addAttribute("fishingBookings", bookings);
        return "admin-book-fishing";
    }

    @PostMapping("/book/fishing/updateStatus")
    public String updateBookFishingStatus(BookInfoFishingDto dto){
        adminService.setBookFishingStatus(dto.getReservationNumber(), dto.getStatus().toString());
        return "redirect:/admin/book/fishing";
    }

    @DeleteMapping("/book/fishing/delete/{id}")
    public String deleteBookFishing(@PathVariable("id") Long id) {
        adminService.deleteBookFishingById(id);
        return "redirect:/admin/book/fishing";
    }
}
