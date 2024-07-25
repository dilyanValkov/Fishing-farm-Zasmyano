package Valkov.Fishing_Farm_Zasmyano.web;

import Valkov.Fishing_Farm_Zasmyano.domain.dto.fishing.FishingSpotInfoDto;
import Valkov.Fishing_Farm_Zasmyano.domain.model.FishingSpot;
import Valkov.Fishing_Farm_Zasmyano.repository.fishing.FishingSpotRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/fishing-spots")
public class FishingRestController {
    private final FishingSpotRepository fishingSpotRepository;
    private final ModelMapper modelMapper;
@GetMapping("/{id}")
    public ResponseEntity<FishingSpotInfoDto> fishingSpotInfo(@PathVariable Long id) {
    if (!fishingSpotRepository.existsById(id)) {
        return ResponseEntity.badRequest().build();
    }
    FishingSpot fishingSpot = fishingSpotRepository.getReferenceById(id);
    return ResponseEntity.ok(modelMapper.map(fishingSpot, FishingSpotInfoDto.class));
    }
}
