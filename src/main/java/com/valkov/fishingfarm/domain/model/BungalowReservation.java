package com.valkov.fishingfarm.domain.model;
import com.valkov.fishingfarm.domain.enums.Status;
import com.valkov.fishingfarm.domain.model.user.User;
import jakarta.persistence.*;
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
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "total_price", nullable = false)
    private BigDecimal totalPrice;

    @Column(nullable = false)
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
        long days = java.time.temporal.ChronoUnit.DAYS.between(startDate, endDate);
        totalPrice = bungalow.getPrice().multiply(BigDecimal.valueOf(days));
    }

    public String statusMessage (String status) {
        StringBuilder sb = new StringBuilder();
        if (status.equals("UNCONFIRMED")){
            sb.append(user.getFirstName())
                    .append(", направихте резервация за бунгало ")
                    .append(bungalow.getId()).append(" за времето от ")
                    .append(startDate).append(" до ").append(endDate).append(".").append(System.lineSeparator())
                    .append("Цена за престоя: ").append(totalPrice).append(" лв.").append(System.lineSeparator())
                    .append("Моля, изчакайте да се свържем с Вас за потвърждение на резервацията.");
        }else if (status.equals("REJECTED")){
            sb.append("Резервацията Ви е отказана.");
        }else if (status.equals("CONFIRMED")){
            sb.append("Резервацията Ви е потвърдена.Моля, заповядайте на риболов и отдих.");
        }
        return sb.toString();
    }

    public String emailContent(){
        return "Относно: направена резервация с No " + getId()+ " за настаняване на язовир Засмяно.";
    }
}
