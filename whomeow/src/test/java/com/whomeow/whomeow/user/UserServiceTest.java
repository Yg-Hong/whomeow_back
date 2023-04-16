package com.whomeow.whomeow.user;

import com.whomeow.whomeow.user.Dto.SignUpRequestDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class UserServiceTest {
    UserService userService;
    @Test
    void signUp() {
        SignUpRequestDto signUpRequestDto = new SignUpRequestDto();
        signUpRequestDto.setUserName("김유민");
        signUpRequestDto.setUserEmail("user@naver.com");
        signUpRequestDto.setUserPassword("1234");

        String email = userService.signUp(signUpRequestDto);

        Assertions.assertThat(email).isEqualTo("user@naver.com");
    }
}