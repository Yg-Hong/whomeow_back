package com.whomeow.whomeow.user.Dto;

import com.whomeow.whomeow.user.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
@NoArgsConstructor
public class UserDto {
    private long id;
    @NotBlank
    @Email
    private String userEmail;
    @NotBlank
    private String phoneNumber;
    @NotBlank
    @Size(min = 1,max = 32)
    private String userPassword;
    @NotBlank
    private String userName;
    private int userWithdraw;
    private Date createDate;
    private Date updateDate;

    /**
     * 암호화된 userPassword
     */
    public void encryptPassword(String BCryptpassword) {
        this.userPassword = BCryptpassword;
    }

    @Builder
    public UserDto(String userEmail, String userPassword, String userName, String phoneNumber, int userWithdraw, Date createDate, Date updateDate) {
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.userName = userName;
        this.phoneNumber = phoneNumber;
        this.userWithdraw = userWithdraw;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }

    public User toEntity() {
        return User.builder()
                .userEmail(this.userEmail)
                .userPassword(this.userPassword)
                .userName(this.userName)
                .phoneNumber(this.phoneNumber)
                .userWithdraw(this.userWithdraw)
                .createDate(this.createDate)
                .updateDate(this.updateDate)
                .build();
    }
}
