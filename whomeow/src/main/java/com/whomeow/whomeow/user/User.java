package com.whomeow.whomeow.user;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.util.Date;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USERKEY")
    private Long userKey;
    @Column(name = "USEREMAIL",nullable = false, unique = true)
    private String userEmail;
    @Column(name = "USERPASSWORD",nullable = false, length = 32)
    private String userPassword;
    @Column(name = "USERNAME",nullable = false)
    private String userName;
    @Column(name = "USERWITHDRAW",nullable = false)
    @ColumnDefault("0")
    private int userWithdraw;
    @Column(name = "CREATEDATE",nullable = false)
    private Date createDate;
    @Column(name = "UPDATEDATE",nullable = false)
    private Date updateDate;

    @Builder
    public User(String userPassword, String userName, String userEmail, Date createDate, Date updateDate, int userWithdraw){
        this.userPassword = userPassword;
        this.userName = userName;
        this.userEmail = userEmail;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.userWithdraw = userWithdraw;
    }
}
