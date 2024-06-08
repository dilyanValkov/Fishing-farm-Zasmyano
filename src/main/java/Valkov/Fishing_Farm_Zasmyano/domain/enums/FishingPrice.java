package Valkov.Fishing_Farm_Zasmyano.domain.enums;

import lombok.Getter;

@Getter
public enum FishingPrice {
    DAY(25),
    NIGHT(25),
    DAY_AND_NIGHT(40);

    private final double price;

    FishingPrice(double price) {
        this.price = price;
    }
}
