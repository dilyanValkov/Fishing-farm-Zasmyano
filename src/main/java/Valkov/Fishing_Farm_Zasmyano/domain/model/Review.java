package Valkov.Fishing_Farm_Zasmyano.domain.model;

import Valkov.Fishing_Farm_Zasmyano.domain.enums.Rating;
import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "reviews")
public class Review extends BaseEntity{

    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private Rating rating;

    @Column(columnDefinition = "TEXT")
    private String comment;

    @OneToMany(targetEntity = Picture.class, mappedBy = "review")
    private Set<Picture> pictures;

    @ManyToOne
    private User author;
}
