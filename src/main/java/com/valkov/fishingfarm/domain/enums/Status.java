package com.valkov.fishingfarm.domain.enums;

import lombok.Getter;

@Getter
public enum Status {

    UNCONFIRMED("непотвърдена"), CONFIRMED("потвърдена"), REJECTED("отхвърлена");

    private final String text;

    Status(String text) {
        this.text = text;
    }
}
