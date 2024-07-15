package Valkov.Fishing_Farm_Zasmyano.domain.model;

import Valkov.Fishing_Farm_Zasmyano.domain.enums.Attitude;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User extends BaseEntity{


    @Column(nullable = false,name = "first_name",length = 50)
    private String firstName;

    @Column(nullable = false,name = "last_name",length = 50)
    private String lastName;

    @Column(unique = true,length = 50, nullable = false)
    private String email;

    @Column(nullable = false,name = "phone_number", unique = true)
    private String phoneNumber;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.ORDINAL)
    private Attitude attitude;

    @OneToMany(mappedBy = "user")
    private List<BungalowReservation> reservations;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<UserRole> roles = new ArrayList<>();

    public String getFullName(){
        return firstName + " " + lastName;
    }
}
