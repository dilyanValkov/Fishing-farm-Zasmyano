package com.valkov.fishingfarm.domain.dto.bungalow;
import com.valkov.fishingfarm.domain.enums.Status;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class BookInfoBungalowDto {
    private Long id;

    private LocalDate startDate;

    private LocalDate endDate;

    private BigDecimal totalPrice;

    private LocalDateTime createdAt;

    private Status status;

    private long bungalowNumber;

    private long reservationNumber;

    private String comment;

    private String userInfo;
}
