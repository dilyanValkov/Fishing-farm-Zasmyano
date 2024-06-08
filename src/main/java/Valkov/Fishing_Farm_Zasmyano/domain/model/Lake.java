package Valkov.Fishing_Farm_Zasmyano.domain.model;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import java.util.Set;

@Getter
@Setter
@Entity
public class Lake extends BaseEntity{

    private String name;

    private String phoneNumber;

    private String facebookUrl;

    private String gpsCoordinates;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String fishingRules;

    @OneToMany(targetEntity = FishingSpot.class, mappedBy = "lake")
    private Set<FishingSpot> fishingSpots;
}
