package com.valkov.fishingfarm.domain.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "bungalows")
public class Bungalow extends BaseEntity{

    @Column(nullable = false)
    private int capacity;

    @Column(nullable = false)
    private BigDecimal price;

    @OneToMany(targetEntity = BungalowReservation.class, mappedBy = "bungalow")
    private List<BungalowReservation> reservations;
}
