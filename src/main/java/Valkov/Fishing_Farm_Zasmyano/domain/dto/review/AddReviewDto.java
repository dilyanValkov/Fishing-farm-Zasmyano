package Valkov.Fishing_Farm_Zasmyano.domain.dto.review;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddReviewDto {
    @Min(1)
    @Max(5)
    @NotNull
    private int rating;

    @Size(min = 5, max = 500)
    @NotNull
    private String comment;
}
