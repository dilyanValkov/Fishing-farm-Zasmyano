package com.valkov.fishingfarm.domain.dto.fishing;

import com.valkov.fishingfarm.domain.enums.FishingHours;
import com.valkov.fishingfarm.domain.enums.Status;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
@Getter
@Setter
public class BookInfoFishingDto {
    private Long id;

    private LocalDate startDate;

    private LocalDate endDate;

    private BigDecimal totalPrice;

    private LocalDateTime createdAt;

    private Status status;

    private long spotNumber;

    private long reservationNumber;

    private int fishermanCount;

    private FishingHours fishingHours;

    private String userInfo;
}
