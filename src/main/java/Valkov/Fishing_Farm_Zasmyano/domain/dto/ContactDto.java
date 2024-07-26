package Valkov.Fishing_Farm_Zasmyano.domain.dto;

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
                + "Съобшение: \n\n"
                + message + ".\n\n" +
                "Email за обратна връзка: " + email;
    }
}
