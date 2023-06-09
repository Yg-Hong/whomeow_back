package com.whomeow.whomeow.user;

import com.whomeow.whomeow.user.Dto.UserDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USERKEY")
    private Long userKey;
    @Column(name = "USEREMAIL", nullable = false, unique = true)
    private String userEmail;
    @Column(name = "USERPASSWORD", nullable = false, length = 32)
    private String userPassword;
    @Column(name = "USERNAME", nullable = false)
    private String userName;
    @Column(name = "PHONENUMBER", nullable = false)
    private String phoneNumber;
    @Column(name = "USERWITHDRAW", nullable = false)
    @ColumnDefault("0")
    private int userWithdraw;
    @Column(name = "CREATEDATE", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createDate;
    @Column(name = "UPDATEDATE", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date updateDate;
    @Column(name = "APPROVALKEY", nullable = false)
    private String approvalKey;

    @Builder
    public User(String userPassword, String userName, String userEmail, String phoneNumber, Date createDate, Date updateDate, int userWithdraw, String approvalKey) {
        this.userPassword = userPassword;
        this.userName = userName;
        this.userEmail = userEmail;
        this.phoneNumber = phoneNumber;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.userWithdraw = userWithdraw;
        this.approvalKey = approvalKey;
    }

    public String updatePassword(String newPassword) {
        this.userPassword = newPassword;

        return newPassword;
    }
}
