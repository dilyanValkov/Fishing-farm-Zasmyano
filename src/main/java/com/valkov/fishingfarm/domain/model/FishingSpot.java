package com.valkov.fishingfarm.domain.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "fishing_spots")
public class FishingSpot extends BaseEntity{

    @Column(nullable = false)
    private int capacity;

    private String description;

    @Column(name = "day_price", nullable = false)
    private BigDecimal dayPrice;

    @Column(name = "day_and_night_price", nullable = false)
    private BigDecimal dayAndNightPrice;

    @OneToMany(targetEntity = FishingReservation.class, mappedBy = "fishingSpot")
    private List<FishingReservation> reservations;
}
