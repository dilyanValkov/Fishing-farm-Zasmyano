package com.valkov.fishingfarm.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContactDto {
    private String name;

    private String email;

    private String message;


    public String form(){
        return "С вас се свърза " + name + ".\n\n"
                + "Съобщение: \n\n"
                + message + ".\n\n" +
                "Email за обратна връзка: " + email;
    }
}
