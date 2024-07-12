package Valkov.Fishing_Farm_Zasmyano.domain.dto;
import Valkov.Fishing_Farm_Zasmyano.domain.model.FishingSpot;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
@Getter
@Setter
public class LakeInfoDTO {
    private String name;

    private String phoneNumber;

    private String facebookUrl;

    private double latitude;

    private double longitude;

    private String description;

    private String fishingRules;

    private Set<FishingSpot> fishingSpots;

}
