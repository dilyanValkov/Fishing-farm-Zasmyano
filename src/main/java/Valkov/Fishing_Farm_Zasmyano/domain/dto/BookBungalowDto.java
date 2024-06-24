package Valkov.Fishing_Farm_Zasmyano.domain.dto;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
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
    @Size(max = 400)
    private String comment;
}
