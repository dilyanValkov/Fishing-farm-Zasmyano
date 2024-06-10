package Valkov.Fishing_Farm_Zasmyano.domain.model;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@Entity
public class FishingSpot extends BaseEntity{

    @Column(nullable = false)
    private int capacity;

    private String description;

    @Column(name = "day_price", nullable = false)
    private BigDecimal dayPrice;

    @Column(name = "night_price", nullable = false)
    private BigDecimal nightPrice;

    @Column(name = "day_and_night_price", nullable = false)
    private BigDecimal dayAndNightPrice;

    @OneToMany(targetEntity = Picture.class, mappedBy = "fishingSpot")
    private Set<Picture> pictures;

    @OneToMany(targetEntity = FishingReservation.class, mappedBy = "fishingSpot")
    private Set<FishingReservation> reservations;

    @ManyToOne
    private Lake lake;
}
