package com.whomeow.whomeow.session;

import com.whomeow.whomeow.user.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

class SessionManagerTest {
    SessionManager sessionManager = new SessionManager();

    @Test
    void sessionTest() {

        //세션 생성: 서버
        MockHttpServletResponse response = new MockHttpServletResponse();
        User user = User.builder().build();
        sessionManager.createSession(user, response);

        //요청에 응답 쿠키 저장 : 웹 브라우저
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setCookies(response.getCookies());

        //세션 조회
        Object result = sessionManager.getSession(request);
        Assertions.assertThat(result).isEqualTo(user);

        //세션 만료
        sessionManager.expires(request);
        Assertions.assertThat(sessionManager.getSession(request)).isNull();
    }
}