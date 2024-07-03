package Valkov.Fishing_Farm_Zasmyano.web;
import Valkov.Fishing_Farm_Zasmyano.service.LakeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class LakeController {
    private final LakeService lakeService;

    @GetMapping("/lake")
    public ModelAndView lakePage(){
        ModelAndView modelAndView = new ModelAndView("lake");
        modelAndView.addObject(this.lakeService.mapLakeInfoDto());
        return modelAndView;
    }
}
