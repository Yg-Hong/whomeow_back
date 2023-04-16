package com.whomeow.whomeow.user.Dto;

import com.whomeow.whomeow.user.User;
import jakarta.validation.constraints.*;
import lombok.*;

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
    public UserDto(String userEmail, String userPassword, String userName, int userWithdraw, Date createDate, Date updateDate) {
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.userName = userName;
        this.userWithdraw = userWithdraw;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }

    public User toEntity() {
        return User.builder()
                .userEmail(this.userEmail)
                .userPassword(this.userPassword)
                .userName(this.userName)
                .userWithdraw(this.userWithdraw)
                .createDate(this.createDate)
                .updateDate(this.updateDate)
                .build();
    }
}
