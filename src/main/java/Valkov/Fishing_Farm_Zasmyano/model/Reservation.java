package Valkov.Fishing_Farm_Zasmyano.model;

import Valkov.Fishing_Farm_Zasmyano.model.enums.FishingPeriod;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Set;

@Entity
@Table(name = "reservations")
public class Reservation extends BaseEntity{

    @Column(name = "reservation_date", nullable = false)
    private LocalDateTime reservationDate;
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "fishing_period")
    private FishingPeriod fishingPeriod;

    @Column(name = "fisherman_count",nullable = false)
    private Integer fishermanCount;

    @Column(name = "arrival_time")
    private LocalTime arrivalTime;

    @OneToMany
    private Set<FishingSpot> fishingSpot;


    @ManyToOne
    private User user;



}
