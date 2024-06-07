package Valkov.Fishing_Farm_Zasmyano.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.Set;

@Entity
@Table(name = "reviews")
public class Review extends BaseEntity{



    @OneToMany
    private Set<Picture> pictures;
}
