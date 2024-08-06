package com.valkov.fishingfarm.domain.model.user;

import com.valkov.fishingfarm.domain.enums.Attitude;
import com.valkov.fishingfarm.domain.model.BaseEntity;
import com.valkov.fishingfarm.domain.model.BungalowReservation;
import com.valkov.fishingfarm.domain.model.FishingReservation;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User extends BaseEntity {

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

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<BungalowReservation> bungalowReservations;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<FishingReservation> fishingReservations;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<UserRole> roles = new ArrayList<>();

    public String getFullName(){
        return firstName + " " + lastName;
    }

    public String getUserInfo(){
        StringBuilder sb = new StringBuilder();
        sb.append(firstName).append(" ").append(lastName)
                .append(System.lineSeparator()).append("/")
                .append(phoneNumber).append("/").append(attitude.getText());
        return sb.toString();
    }
}
