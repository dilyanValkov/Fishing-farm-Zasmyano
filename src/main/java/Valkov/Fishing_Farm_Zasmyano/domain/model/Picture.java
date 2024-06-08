package Valkov.Fishing_Farm_Zasmyano.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "pictures")
public class Picture extends BaseEntity{

    @Column()
    private String tittle;

    @Column(nullable = false)
    private String pictureUrl;

    @ManyToOne
    private User author;

    @ManyToOne
    private FishingSpot fishingSpot;

    @ManyToOne
    private Bungalow bungalow;

    @ManyToOne
    private Review review;


}
