package Valkov.Fishing_Farm_Zasmyano.domain.model;

import Valkov.Fishing_Farm_Zasmyano.domain.enums.Attitude;
import Valkov.Fishing_Farm_Zasmyano.domain.enums.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class User extends BaseEntity{

    @Column(name = "user_name", nullable = false, unique = true)
    private String userName;

    @Column(nullable = false,name = "first_name")
    private String firstName;

    @Column(nullable = false,name = "last_name")
    private String lastName;

    @Column(unique = true)
    private String email;

    @Column(nullable = false,name = "phone_number")
    private String phoneNumber;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.ORDINAL)
    private Role role;

    @Enumerated(EnumType.ORDINAL)
    private Attitude attitude;

    @OneToMany(targetEntity = BungalowReservation.class, mappedBy = "user")
    private List<BungalowReservation> reservations;

}
