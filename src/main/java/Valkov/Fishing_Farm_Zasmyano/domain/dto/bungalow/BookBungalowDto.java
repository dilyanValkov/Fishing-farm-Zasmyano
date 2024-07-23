package Valkov.Fishing_Farm_Zasmyano.domain.dto.bungalow;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class BookBungalowDto {

    @NotNull
    @Positive
    private Long number;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

    @Size(max = 25)
    private String comment;
}
