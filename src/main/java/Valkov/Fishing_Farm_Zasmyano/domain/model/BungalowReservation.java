package Valkov.Fishing_Farm_Zasmyano.domain.model;
import Valkov.Fishing_Farm_Zasmyano.domain.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
@Getter
@Setter
@Entity
@Table(name = "bungalow_reservations")
public class BungalowReservation extends BaseEntity{

    @Column(name = "start_date", nullable = false)
    @Future
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    @Future
    private LocalDate endDate;

    @Column(name = "total_price", nullable = false)
    private BigDecimal totalPrice;

    @Column(nullable = false)
    @Future
    private LocalDateTime createdAt = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "comments")
    private String comment;

    @ManyToOne
    private Bungalow bungalow;

    @ManyToOne
    private User user;

    @PrePersist
    public void calculateTotalPrice() {
        long days = java.time.temporal.ChronoUnit.DAYS.between(startDate, endDate) + 1;
        totalPrice = bungalow.getPrice().multiply(BigDecimal.valueOf(days));
    }
}
