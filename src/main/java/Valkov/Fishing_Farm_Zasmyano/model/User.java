package Valkov.Fishing_Farm_Zasmyano.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User extends BaseEntity{

    @Column(nullable = false,name = "first_name")
    private String firstName;

    @Column(nullable = false,name = "last_name")
    private String lastName;

    @Column(nullable = false,unique = true)
    private String email;

    @Column(nullable = false,name = "phone_number")
    private String phoneNumber;

    //TODO List reservations

}
