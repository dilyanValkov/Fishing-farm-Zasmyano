package Valkov.Fishing_Farm_Zasmyano.model;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
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


    //TODO List fishingSpot
}
