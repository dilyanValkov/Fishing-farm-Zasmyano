package Valkov.Fishing_Farm_Zasmyano.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
@Getter
@Setter
@Entity
@Table(name = "bungalows")
public class Bungalow extends BaseEntity{

    private Integer capacity;

    private Integer price;

    @OneToMany(targetEntity = Picture.class, mappedBy = "bungalow")
    private Set<Picture> pictures;

    @ManyToOne
    private Reservation reservation;
}
