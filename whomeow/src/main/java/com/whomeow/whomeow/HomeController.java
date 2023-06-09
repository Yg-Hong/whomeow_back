package com.whomeow.whomeow;

import com.whomeow.whomeow.session.SessionConst;
import com.whomeow.whomeow.session.SessionManager;
import com.whomeow.whomeow.user.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final SessionManager sessionManager;

    @GetMapping("/")
    public String homeLogin(HttpServletRequest request, Model model) {
        // request.getSession(false): request.getSession()를 사용하면
        // 기본값이 create : true 이므로, 로그인 하지 않을 사용자도 의미없는 세션이
        //만들어진다. 따라서 세션을 찾아서 사용하는 시점에는 create:false옵션을 사용해
        // 세션을 생성하지 않아야 한다.
        HttpSession session = request.getSession(false);

        if(session == null){
            return "main";
        }

        User loginUser = (User)
                // 로그인 시점에 세션에 보관한 회원 객체를 찾는다.
                session.getAttribute(SessionConst.LOGIN_USER);
        //세션에 회원 데이터가 없으면 home
        if(loginUser == null){
            return "main";
        }
        model.addAttribute("member", loginUser);
        return "loginHome";
    }

    @GetMapping("/profile")
    public String profile(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);

        if(session == null){
            return "main";
        }

        User loginUser = (User) session.getAttribute(SessionConst.LOGIN_USER);
        if(loginUser == null){
            return "main";
        }

        return "profile";
    }

    @GetMapping("/status")
    public String status(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);

        if (session == null) {
            return "main";
        }

        User loginUser = (User) session.getAttribute(SessionConst.LOGIN_USER);
        if (loginUser == null) {
            return "main";
        }

        return "status";
    }
}
