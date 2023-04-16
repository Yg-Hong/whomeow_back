package com.whomeow.whomeow.user.Dto;

import com.whomeow.whomeow.user.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpRequestDto {
    private String userEmail;
    private String userPassword;
    private String userName;
    private boolean policyAgreement;
    public User toEntity(){
        return User
                .builder()
                .userEmail(this.userEmail)
                .userPassword(this.userPassword)
                .userName(this.userName)
                .build();
    }

    public UserDto toDto() {
        return UserDto
                .builder()
                .userEmail(this.userEmail)
                .userPassword(this.userPassword)
                .userName(this.userName)
                .build();
    }
}
