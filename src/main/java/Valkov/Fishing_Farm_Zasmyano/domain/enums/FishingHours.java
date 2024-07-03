package Valkov.Fishing_Farm_Zasmyano.domain.enums;

import lombok.Getter;

@Getter
public enum FishingHours {
 DAY("Дневен"), DAY_AND_NIGHT("Денонощен");

 private final String text;

 FishingHours(String text) {
  this.text = text;
 }

}
