package com.whomeow.whomeow.profile;

import com.whomeow.whomeow.session.SessionConst;
import com.whomeow.whomeow.user.User;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/profile/*")
public class ProfileInfoController {
    private final ProfileService profileService;

    @RequestMapping(value = "/showProfile", method = RequestMethod.GET)
    public Dog showProfile(HttpSession session) {
        User user = (User) session.getAttribute(SessionConst.LOGIN_USER);
        Dog dog = profileService.showProfile(user.getUserEmail());
        return dog;
    }

    @RequestMapping(value = "/editProfile", method = RequestMethod.POST)
    public Dog editProfile(HttpSession session, @RequestBody DogDto dogDto){
        User user = (User) session.getAttribute(SessionConst.LOGIN_USER);
        Dog dog = profileService.editProfile(user.getUserEmail(), dogDto);
        return dog;
    }
}
