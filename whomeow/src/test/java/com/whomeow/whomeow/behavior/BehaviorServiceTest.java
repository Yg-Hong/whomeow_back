package com.whomeow.whomeow.behavior;

import com.whomeow.whomeow.profile.Dog;
import com.whomeow.whomeow.profile.DogDto;
import com.whomeow.whomeow.profile.ProfileService;
import com.whomeow.whomeow.user.Dto.SignUpRequestDto;
import com.whomeow.whomeow.user.User;
import com.whomeow.whomeow.user.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class BehaviorServiceTest {

    @Autowired
    private BehaviorJpaRepository behaviorJpaRepository;
    @Autowired
    private ProfileService profileService;
    @Autowired
    private UserService userService;

    @Test
    void getWeeklyBehavior() {
    }
}