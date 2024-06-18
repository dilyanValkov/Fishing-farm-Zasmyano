package Valkov.Fishing_Farm_Zasmyano.config;

import Valkov.Fishing_Farm_Zasmyano.domain.model.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Getter
@Setter
@Component
@SessionScope
public class UserSession {
    private long id;

    private String email;

    public void login(User user){
    this.id = user.getId();
    this.email = user.getEmail();
    }

    public boolean isUserLoggedIn(){
       return this.id  != 0;
    }

    public void logout(){
        this.id = 0;
        this.email = "";
    }

}
