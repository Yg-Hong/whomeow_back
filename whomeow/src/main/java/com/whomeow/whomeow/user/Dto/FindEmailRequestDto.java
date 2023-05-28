package com.whomeow.whomeow.user.Dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FindEmailRequestDto {
    String userName;
    String phoneNumber;

    @Builder
    public FindEmailRequestDto(String userName, String phoneNumber) {
        this.userName = userName;
        this.phoneNumber = phoneNumber;
    }
}
