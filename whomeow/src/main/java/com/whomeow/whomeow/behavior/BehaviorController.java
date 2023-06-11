package com.whomeow.whomeow.behavior;

import com.whomeow.whomeow.session.SessionConst;
import com.whomeow.whomeow.user.User;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping(value = "/status")
@RequiredArgsConstructor
public class BehaviorController {

    private final BehaviorService behaviorService;

    @ResponseBody
    @GetMapping(value = "/showWeeklyBehavior")
    public HashMap<String, Object> getWeeklyBehavior(HttpSession session) {
        User user = (User) session.getAttribute(SessionConst.LOGIN_USER);

        return behaviorService.getWeeklyBehavior(user);
    }

    @ResponseBody
    @GetMapping(value = "/showProfile")
    public HashMap<String, Object> getProfile(HttpSession session) {
        User user = (User) session.getAttribute(SessionConst.LOGIN_USER);

        return behaviorService.getProfile(user);
    }
}
