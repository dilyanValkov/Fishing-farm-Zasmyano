package com.valkov.fishingfarm.domain.dto.fishing;
import com.valkov.fishingfarm.domain.enums.FishingHours;
import com.valkov.fishingfarm.domain.enums.FishingSpotNumber;
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
}
