package Valkov.Fishing_Farm_Zasmyano.domain.model;
import Valkov.Fishing_Farm_Zasmyano.domain.enums.FishingHours;
import Valkov.Fishing_Farm_Zasmyano.domain.enums.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "fishing_reservations")
public class FishingReservation extends BaseEntity{

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "fisherman_count",nullable = false)
    private Integer fishermanCount;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "fishing_hours")
    private FishingHours fishingHours;

    @Column(nullable = false)
    private BigDecimal totalPrice;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne
    private FishingSpot fishingSpot;

    @ManyToOne
    private User user;
    @PrePersist
    public void calculateTotalPrice(){
        BigDecimal price = BigDecimal.ZERO;
        switch (fishingHours){
            case DAY -> price = fishingSpot.getDayPrice();
            case NIGHT -> price = fishingSpot.getNightPrice();
            case DAY_AND_NIGHT -> price = fishingSpot.getDayAndNightPrice();
        }
        long days = java.time.temporal.ChronoUnit.DAYS.between(startDate, endDate);
        totalPrice = price.multiply(BigDecimal.valueOf(days)).multiply(BigDecimal.valueOf(fishermanCount));
    }
}
