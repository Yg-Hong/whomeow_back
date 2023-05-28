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


    @Test
    void resetApprovalKey() {
        String key1 = userService.resetApprovalKey();
        String key2 = userService.resetApprovalKey();

        System.out.println("key1 : " + key1);
        System.out.println("key2 : " + key2);

        Assertions.assertThat(key1).isNotEqualTo(key2);
    }


    @Test
    @Transactional
    void sendMail() {
        SignUpRequestDto signUpRequestDto = new SignUpRequestDto();
        signUpRequestDto.setUserName("김유민");
        signUpRequestDto.setUserEmail("lion0077v@gmail.com");
        signUpRequestDto.setUserPassword("1234");
        signUpRequestDto.setPhoneNumber("010-0000-0000");

        User user = userService.signUp(signUpRequestDto);

        userService.sendMail("lion0077v@gmail.com");
    }

    @Test
    @Transactional
    void resetPassword() {
        SignUpRequestDto signUpRequestDto = new SignUpRequestDto();
        signUpRequestDto.setUserName("김유민");
        signUpRequestDto.setUserEmail("lion0077v@gmail.com");
        signUpRequestDto.setUserPassword("1234");
        signUpRequestDto.setPhoneNumber("010-0000-0000");

        User user = userService.signUp(signUpRequestDto);

        String password = userService.resetPassword(user.getUserEmail(), "4321", "4321");

        Assertions.assertThat(password).isEqualTo("4321");
    }
}