package Valkov.Fishing_Farm_Zasmyano.model.enums;
import lombok.Getter;

@Getter
public enum FishingSpotNumber {
    SPOT1(1), SPOT2(2), SPOT3(3), SPOT4(4), SPOT5(5), SPOT6(6), SPOT(7), SPOT8(8), SPOT9(9),
    SPOT10(10), SPOT11(11), SPOT12(12), SPOT13(13), SPOT14(14), SPOT15(15), SPOT16(16), SPOT17(17),
    SPOT18(18), SPOT19(19), SPOT20(20), SPOT21(21), SPOT22(22), SPOT23(23), SPOT24(24);

    private final int number;
    FishingSpotNumber(int number) {
        this.number = number;
    }
}
