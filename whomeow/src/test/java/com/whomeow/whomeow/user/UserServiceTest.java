package com.whomeow.whomeow.user;

import com.whomeow.whomeow.user.Dto.FindEmailRequestDto;
import com.whomeow.whomeow.user.Dto.SignUpRequestDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class UserServiceTest {
    @Autowired
    private UserService userService;
    @Autowired
    private UserJpaRepository userJpaRepository;


    @Test
    @DisplayName("회원가입 테스트")
    @Transactional
    void signUp() {
        SignUpRequestDto signUpRequestDto = new SignUpRequestDto();
        signUpRequestDto.setUserName("김유민");
        signUpRequestDto.setUserEmail("user@naver.com");
        signUpRequestDto.setUserPassword("1234");
        signUpRequestDto.setPhoneNumber("010-0000-0000");

        User user = userService.signUp(signUpRequestDto);

        Assertions.assertThat(user.getUserEmail()).isEqualTo("user@naver.com");
    }

    @Test
    @DisplayName("이메일 찾기")
    @Transactional
    void findEmail() {
        SignUpRequestDto signUpRequestDto = new SignUpRequestDto();
        signUpRequestDto.setUserName("김유민");
        signUpRequestDto.setUserEmail("user@naver.com");
        signUpRequestDto.setUserPassword("1234");
        signUpRequestDto.setPhoneNumber("010-0000-0000");

        User user = userService.signUp(signUpRequestDto);

        FindEmailRequestDto findEmailRequestDto = FindEmailRequestDto.
                builder().
                userName("김유민").
                phoneNumber("010-0000-0000").
                build();

        String email = userService.findEmail(findEmailRequestDto);

        Assertions.assertThat(email).isEqualTo(user.getUserEmail());
    }


}