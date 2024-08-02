package com.valkov.fishingfarm.domain.enums;

import lombok.Getter;

@Getter
public enum FishingSpotNumber {
    ONE(1L), TWO(2L), THREE(3L), FOUR(4L), FIVE(5L),
    SIX(6L), SEVEN(7L), EIGHT(8L), NINE(9L), TEN(10L);

    private final Long number;

    FishingSpotNumber(Long number) {
        this.number = number;
    }
}
