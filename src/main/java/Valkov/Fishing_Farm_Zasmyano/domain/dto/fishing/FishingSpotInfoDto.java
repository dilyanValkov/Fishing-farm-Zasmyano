package Valkov.Fishing_Farm_Zasmyano.domain.dto.fishing;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class FishingSpotInfoDto {

    private Long number;

    private int capacity;

    private String description;

    private BigDecimal dayPrice;

    private BigDecimal dayAndNightPrice;
}
