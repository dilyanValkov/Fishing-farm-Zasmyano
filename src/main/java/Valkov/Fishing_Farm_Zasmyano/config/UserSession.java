package Valkov.Fishing_Farm_Zasmyano.config;
import lombok.Getter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Getter
@Component
@SessionScope
public class UserSession {
    private long id;

    private String email;

}
