package Valkov.Fishing_Farm_Zasmyano.domain.model;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "lakes")
public class Lake extends BaseEntity{

    @Column(length = 20, nullable = false)
    private String name;

    @Column(length = 20, nullable = false)
    private String phoneNumber;

    @Column(length = 150)
    private String facebookUrl;

    private double latitude;

    private double longitude;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String fishingRules;

    @OneToMany(targetEntity = FishingSpot.class, mappedBy = "lake")
    private List<FishingSpot> fishingSpots;

}
