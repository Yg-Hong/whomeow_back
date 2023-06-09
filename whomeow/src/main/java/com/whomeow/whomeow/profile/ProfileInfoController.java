package com.whomeow.whomeow.profile;

import com.whomeow.whomeow.session.SessionConst;
import com.whomeow.whomeow.user.User;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/profile/*")
public class ProfileInfoController {
    private final ProfileService profileService;
    private final ProfileJpaRepository profileJpaRepository;

    @RequestMapping(value = "/showProfile", method = RequestMethod.GET)
    public ModelAndView showProfile(HttpSession session) {
        User user = (User) session.getAttribute(SessionConst.LOGIN_USER);
        ModelAndView view = new ModelAndView();

        Optional<Dog> optionalDog = profileJpaRepository.findByUser(user);
        if (optionalDog.isEmpty()) {
            view.setViewName("profile");
            view.addObject("editable", false);

            Dog dog = profileService.showProfile(user.getUserEmail());
            view.addObject("profile", dog);
        } else {
            view.setViewName("profile");
            view.addObject("editable", true);
            Date now = new Date();
            Dog dog = Dog.builder()
                    .dogPhoto("")
                    .dogName("")
                    .dogAge(0)
                    .dogSex("")
                    .dogWeight(0L)
                    .dogBread("")
                    .user(user)
                    .createdAt(now)
                    .updatedAt(now)
                    .build();

            view.addObject("profile", dog);
        }
        return view;
    }

    @RequestMapping(value = "/editProfile", method = RequestMethod.POST)
    public ModelAndView editProfile(HttpSession session, @RequestBody DogDto dogDto){
        User user = (User) session.getAttribute(SessionConst.LOGIN_USER);
        Dog dog = profileService.editProfile(user, dogDto);

        ModelAndView view = new ModelAndView();
        view.setViewName("profile");
        view.addObject("profile", dog);

        return view;
    }
}
