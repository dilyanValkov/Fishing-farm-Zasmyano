package Valkov.Fishing_Farm_Zasmyano.web;

import Valkov.Fishing_Farm_Zasmyano.domain.dto.LakeInfoDTO;
import Valkov.Fishing_Farm_Zasmyano.service.LakeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LakeController {
    private final LakeService lakeService;

    public LakeController(LakeService lakeService) {
        this.lakeService = lakeService;
    }

    @GetMapping("/lake")
    public ModelAndView lakePage(){
        ModelAndView modelAndView = new ModelAndView("lake");
        modelAndView.addObject(this.lakeService.mapLakeInfoDto());
        return modelAndView;
    }
}
