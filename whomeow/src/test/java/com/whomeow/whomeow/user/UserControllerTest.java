package com.whomeow.whomeow.user;

import com.whomeow.whomeow.exception.UserException;
import com.whomeow.whomeow.session.SessionConst;
import com.whomeow.whomeow.user.Dto.SignInRequestDto;
import com.whomeow.whomeow.user.Dto.SignUpRequestDto;
import jakarta.servlet.http.HttpSession;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class UserControllerTest {

    @Autowired
    UserService userService;
    @Autowired
    UserController userController;
    @MockBean
    private BindingResult bindingResult;

    @Test
    @Transactional
    void sessionTest() {
        SignUpRequestDto signUpRequestDto = new SignUpRequestDto();
        signUpRequestDto.setUserName("김유민");
        signUpRequestDto.setUserEmail("user@naver.com");
        signUpRequestDto.setUserPassword("1234");
        signUpRequestDto.setPhoneNumber("010-0000-0000");

        User signUpUser = userService.signUp(signUpRequestDto);

        SignInRequestDto signInRequestDto = new SignInRequestDto();
        signInRequestDto.setUserEmail("user@naver.com");
        signInRequestDto.setUserPassword("1234");

        MockHttpServletRequest req = new MockHttpServletRequest();
        HttpSession session = req.getSession();

        try {
            userController.signIn(signInRequestDto, req, bindingResult);

            User user = (User) session.getAttribute(SessionConst.LOGIN_USER);

            Assertions.assertThat(user.getUserKey()).isEqualTo(signUpUser.getUserKey());

        } catch (UserException ex) {
            throw new RuntimeException(ex);
        }
    }
}