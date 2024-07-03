package Valkov.Fishing_Farm_Zasmyano.domain.enums;

import lombok.Getter;

@Getter
public enum FishingHours {
 DAY("12ч. дневен"), NIGHT("12ч. нощен"), DAY_AND_NIGHT("24ч. денонощен");

 private final String text;

 FishingHours(String text) {
  this.text = text;
 }

}
