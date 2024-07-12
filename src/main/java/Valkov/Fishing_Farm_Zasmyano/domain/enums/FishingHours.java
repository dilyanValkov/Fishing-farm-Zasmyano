package Valkov.Fishing_Farm_Zasmyano.domain.enums;

import lombok.Getter;

@Getter
public enum FishingHours {
 DAY("дневен"), DAY_AND_NIGHT("денонощен");

 private final String text;

 FishingHours(String text) {
  this.text = text;
 }

}
