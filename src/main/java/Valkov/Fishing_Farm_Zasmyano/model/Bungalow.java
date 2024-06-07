package Valkov.Fishing_Farm_Zasmyano.model;

import jakarta.persistence.Entity;
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

    @OneToMany
    private Set<Picture> pictures;
}
