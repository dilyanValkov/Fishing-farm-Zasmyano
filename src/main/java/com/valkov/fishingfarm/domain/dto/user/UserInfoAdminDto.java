package com.valkov.fishingfarm.domain.dto.user;

import com.valkov.fishingfarm.domain.dto.bungalow.BookInfoBungalowDto;
import com.valkov.fishingfarm.domain.dto.fishing.BookInfoFishingDto;
import com.valkov.fishingfarm.domain.enums.Attitude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserInfoAdminDto {
    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private Attitude attitude;

    private List<BookInfoBungalowDto> bungalowReservations;

    private List<BookInfoFishingDto> fishingReservations;

    private String role;
}
