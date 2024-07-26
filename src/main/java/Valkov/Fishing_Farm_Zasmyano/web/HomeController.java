package Valkov.Fishing_Farm_Zasmyano.web;

import Valkov.Fishing_Farm_Zasmyano.domain.dto.ContactDto;
import Valkov.Fishing_Farm_Zasmyano.service.impl.EmailService;
import Valkov.Fishing_Farm_Zasmyano.service.impl.WeatherService;
import Valkov.Fishing_Farm_Zasmyano.service.user.UserUtilService;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequiredArgsConstructor
public class HomeController {
    private final WeatherService weatherService;
    private final UserUtilService userUtilService;
    private final EmailService emailService;

    @Value("${google.maps.api-key}")
    private String googleMapsApiKey;

    @Value("${spring.mail.properties.mail.api.to}")
    private String businessEmail;

    private final String EMAIL_CONTENT = "Връзка с клиент";

    @GetMapping("/")
    public String viewIndex(){
       return "index";
    }

    @GetMapping("/home")
    public String viewHome(Model model){
        
        double latitude = 43.402563;
        double longitude = 27.716726;

        JsonNode weatherData = weatherService.getWeather(latitude, longitude);
        model.addAttribute("temperature", weatherData.path("main").path("temp").asText());
        model.addAttribute("wind", weatherData.path("wind").path("speed").asText());

        String rain = "без валеж";
        if (weatherData.has("rain") && weatherData.path("rain").has("1h")) {
            rain = weatherData.path("rain").path("1h").asText();
        }
        model.addAttribute("rain", rain);

        String userFullName = userUtilService.getCurrentUser().getFullName();
        model.addAttribute("user", userFullName);
        return "home";
    }

    @GetMapping("/about")
    public String viewAbout(){
        return "about";
    }

    @GetMapping("/contact")
    public String viewContact(Model model){
        double latitude = 43.397954;
        double longitude = 27.718773;
        model.addAttribute("googleMapsApiKey", googleMapsApiKey);
        model.addAttribute("latitude", latitude);
        model.addAttribute("longitude", longitude);
        return "contact";
    }

    @PostMapping("/contact")
    public String sendForm(ContactDto dto){
        emailService.sendSimpleEmail(businessEmail, EMAIL_CONTENT, dto.form());
        return "redirect:/";
    }
}
