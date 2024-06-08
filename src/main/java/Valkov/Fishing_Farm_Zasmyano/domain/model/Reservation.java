package Valkov.Fishing_Farm_Zasmyano.domain.model;

import Valkov.Fishing_Farm_Zasmyano.domain.enums.FishingPeriod;
import Valkov.Fishing_Farm_Zasmyano.domain.enums.Status;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Set;

@Entity
@Table(name = "reservations")
public class Reservation extends BaseEntity{

    @Column(name = "reservation_date", nullable = false)
    private LocalDateTime reservationDate;

    @Column(name = "reservation_start_date", nullable = false)
    private LocalDate reservationStartDate;

    @Column(name = "reservation_end_date", nullable = false)
    private LocalDate reservationEndDate;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "fishing_period")
    private FishingPeriod fishingPeriod;

    @Column(name = "fisherman_count",nullable = false)
    private Integer fishermanCount;

    @Column(name = "arrival_time")
    private LocalTime arrivalTime;

    @Column(name = "total_price")
    private double totalPrice;

    private Status status;

    @Column(name = "comments")
    private String comment;

    @OneToMany(targetEntity = FishingSpot.class, mappedBy = "reservation")
    private Set<FishingSpot> fishingSpot;

    @OneToMany(targetEntity = Bungalow.class, mappedBy = "reservation")
    private Set<Bungalow>bungalows;

    @ManyToOne
    private User user;



}
