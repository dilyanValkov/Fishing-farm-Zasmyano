package Valkov.Fishing_Farm_Zasmyano.model;

import Valkov.Fishing_Farm_Zasmyano.model.enums.FishingPeriod;
import Valkov.Fishing_Farm_Zasmyano.model.enums.Status;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

@Entity
@Table(name = "reservations")
public class Reservation extends BaseEntity{

    @Column(name = "reservation_date", nullable = false)
    private LocalDate reservationDate;

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

    private Status comment;
    @OneToMany
    private Set<FishingSpot> fishingSpot;

    @OneToMany()
    private Set<Bungalow>bungalows;
    @ManyToOne
    private User user;



}
