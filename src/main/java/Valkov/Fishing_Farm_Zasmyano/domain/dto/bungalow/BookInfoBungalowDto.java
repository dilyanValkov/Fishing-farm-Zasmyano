package Valkov.Fishing_Farm_Zasmyano.domain.dto.bungalow;
import Valkov.Fishing_Farm_Zasmyano.domain.enums.Status;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class BookInfoBungalowDto {

    private LocalDate startDate;

    private LocalDate endDate;

    private BigDecimal totalPrice;

    private LocalDateTime createdAt;

    private Status status;

    private long bungalowNumber;

    private long reservationNumber;

    private String comment;

}
