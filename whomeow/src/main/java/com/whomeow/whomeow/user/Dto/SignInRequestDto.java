package com.whomeow.whomeow.user.Dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * setter는 test를 위해 둔거지 service 단에서 사용하면 안됩니다.
 */
@Getter
@Setter
@NoArgsConstructor
public class SignInRequestDto {
    private String userEmail;
    private String userPassword;

    @Builder
    public SignInRequestDto(String userEmail, String userPassword) {
        this.userEmail = userEmail;
        this.userPassword = userPassword;
    }
}
