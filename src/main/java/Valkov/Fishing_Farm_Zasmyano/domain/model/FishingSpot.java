package Valkov.Fishing_Farm_Zasmyano.domain.model;
import Valkov.Fishing_Farm_Zasmyano.domain.enums.FishingPrice;
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
@Table(name = "fishing_spots")
public class FishingSpot extends BaseEntity{

    private int capacity;

    private FishingPrice fishingPrice;

    @OneToMany(targetEntity = Picture.class, mappedBy = "fishingSpot")
    private Set<Picture> pictures;

    @ManyToOne
    private Reservation reservation;

    @ManyToOne
    private Lake lake;

}
