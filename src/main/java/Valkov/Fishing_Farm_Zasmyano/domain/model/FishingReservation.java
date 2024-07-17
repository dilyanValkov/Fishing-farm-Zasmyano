package Valkov.Fishing_Farm_Zasmyano.domain.model;
import Valkov.Fishing_Farm_Zasmyano.domain.enums.FishingHours;
import Valkov.Fishing_Farm_Zasmyano.domain.enums.Status;
import Valkov.Fishing_Farm_Zasmyano.domain.model.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "fishing_reservations")
public class FishingReservation extends BaseEntity{

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "fisherman_count",nullable = false)
    private Integer fishermanCount;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "fishing_hours")
    private FishingHours fishingHours;

    @Column(nullable = false)
    private BigDecimal totalPrice;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne
    private FishingSpot fishingSpot;

    @ManyToOne
    private User user;
    @PrePersist
    public void calculateTotalPrice(){
        BigDecimal price = BigDecimal.ZERO;
        switch (fishingHours){
            case DAY -> price = fishingSpot.getDayPrice();
            case DAY_AND_NIGHT -> price = fishingSpot.getDayAndNightPrice();
        }
        long days = java.time.temporal.ChronoUnit.DAYS.between(startDate, endDate)+1;
        totalPrice = price.multiply(BigDecimal.valueOf(days)).multiply(BigDecimal.valueOf(fishermanCount));
    }

    public String toBeConfirmed() {
        StringBuilder sb = new StringBuilder();
        sb.append(user.getFirstName())
                .append(", направихте резервация за риболовно място No ").append(fishingSpot.getId());
                if (startDate.isEqual(endDate)){
                    sb.append(" за ").append(startDate).append(System.lineSeparator());
                }else {
                    sb.append(" за времето от ")
                      .append(startDate).append(" до ").append(endDate).append(System.lineSeparator());
                }
                sb.append(" за ").append(fishermanCount);
                if(fishermanCount==1){
                  sb.append(" риболовец.").append(System.lineSeparator());
                }else {
                  sb.append(" риболовци.").append(System.lineSeparator());
                }

                sb.append("Цена за риболова: ").append(totalPrice).append(" лв.").append(System.lineSeparator())
                .append("Моля, изчакайте да се свържем с Вас за потвърждение на резервацията.");
        return sb.toString();
    }

    public String emailContent(){
        return "Относно: направена резервация с No " + getId()+ " за риболов на язовир Засмяно." ;
    }
}
