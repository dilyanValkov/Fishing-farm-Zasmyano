package Valkov.Fishing_Farm_Zasmyano.model;
import Valkov.Fishing_Farm_Zasmyano.model.enums.FishingPrice;
import jakarta.persistence.Entity;
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

    @OneToMany
    private Set<Picture> pictures;

}