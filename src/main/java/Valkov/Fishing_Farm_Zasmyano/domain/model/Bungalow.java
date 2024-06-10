package Valkov.Fishing_Farm_Zasmyano.domain.model;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Set;
@Getter
@Setter
@Entity
public class Bungalow extends BaseEntity{

    @Column(nullable = false)
    private int capacity;

    @Column(nullable = false)
    private BigDecimal price;

    @OneToMany(targetEntity = Picture.class, mappedBy = "bungalow")
    private Set<Picture> pictures;

    @OneToMany(targetEntity = BungalowReservation.class, mappedBy = "bungalow")
    private Set<BungalowReservation> reservations;
}
