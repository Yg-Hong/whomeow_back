package com.whomeow.whomeow.model;

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
@Table(name="User")
public class UserEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userKey;
    @Column(nullable = false, length = 32)
    private String userId;
    @Column(nullable = false, length = 32)
    private String userPassword;
    @Column(nullable = false)
    private String userName;
    @Column(nullable = false)
    private String userEmail;
    @Column(nullable = false)
    @ColumnDefault("0")
    private int userWithdraw;
    @Column(nullable = false)
    private Date createdAt;
    @Column(nullable = false)
    private Date updatedAt;

    @Builder
    public UserEntity(String userId, String userPassword, String userName, String userEmail, int userWithdraw, Date createdAt, Date updatedAt){
        this.userId = userId;
        this.userPassword = userPassword;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userWithdraw = userWithdraw;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
