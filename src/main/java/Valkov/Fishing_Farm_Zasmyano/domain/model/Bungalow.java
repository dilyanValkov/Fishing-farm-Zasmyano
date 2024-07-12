package Valkov.Fishing_Farm_Zasmyano.domain.model;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;
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
    private Set<BungalowReservation> reservations;


}
