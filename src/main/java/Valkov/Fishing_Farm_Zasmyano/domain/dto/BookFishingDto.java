package Valkov.Fishing_Farm_Zasmyano.domain.dto;
import Valkov.Fishing_Farm_Zasmyano.domain.enums.FishingHours;
import Valkov.Fishing_Farm_Zasmyano.domain.enums.FishingSpotNumber;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.NumberFormat;

import java.time.LocalDate;

@Getter
@Setter
public class BookFishingDto {
    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

    @NotNull
    @NumberFormat
    @Min(1)
    @Max(4)
    private Integer fishermanCount;

    @NotNull
    private FishingHours fishingHours;

    @NotNull
    private FishingSpotNumber fishingSpot;

    @Size(max = 400)
    private String comment;

}
