package com.whomeow.whomeow.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private long userKey;
    private String userId;
    private String userPassword;
    private String userName;
    private String userEmail;
    private int userWithdraw;
    private Date createdAt;
    private Date updatedAt;
}
