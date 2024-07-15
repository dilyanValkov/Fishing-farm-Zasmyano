package Valkov.Fishing_Farm_Zasmyano.domain.dto.review;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ReviewDto {

    private int rating;

    private String comment;

    private Long user;

    private String fullName;

    private LocalDateTime createdAt;
}
