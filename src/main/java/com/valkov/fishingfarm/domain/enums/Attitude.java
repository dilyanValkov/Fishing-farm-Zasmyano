package com.valkov.fishingfarm.domain.enums;

import lombok.Getter;

@Getter
public enum Attitude {
    VERY_BAD("Много лошо"), BAD("Лошо"), GOOD("Добро"), VERY_GOOD("Много добро"), EXCELLENT("Отлично");
    private final String text;
    Attitude(String text) {
        this.text = text;
    }
}
