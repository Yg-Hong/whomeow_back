package com.whomeow.whomeow.profile;

import com.whomeow.whomeow.user.Dto.SignUpRequestDto;
import com.whomeow.whomeow.user.User;
import com.whomeow.whomeow.user.UserJpaRepository;
import com.whomeow.whomeow.user.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class ProfileServiceTest {

    @Autowired
    private ProfileService profileService;
    @Autowired
    private UserJpaRepository userJpaRepository;

//    @Test
//    @Transactional
//    void editProfile() {
//        User user = User.builder().userName("홍윤기").userEmail("user@naver.com").userPassword("1234")
//                .phoneNumber("010-0000-0000").approvalKey("01234566789").createDate(new Date()).updateDate(new Date()).build();
//        DogDto dogDto = new DogDto("www.xxx.com/photoId=1234", "abc", 12, "male", 14, "aaa", user, new Date(), new Date());
//        Dog save = profileService.editProfile("user@naver.com", dogDto);
//
//        Assertions.assertThat(save.getUser()).isEqualTo(user);
//    }
//
//    @Test
//    @Transactional
//    void showProfile() {
//        User user = User.builder().userName("홍윤기").userEmail("user@naver.com").userPassword("1234")
//                .phoneNumber("010-0000-0000").approvalKey("01234566789").createDate(new Date()).updateDate(new Date()).build();
//        DogDto dogDto = new DogDto("www.xxx.com/photoId=1234", "abc", 12, "male", 14, "aaa", user, new Date(), new Date());
//        Dog save = profileService.editProfile("user@naver.com", dogDto);
//        Dog show = profileService.showProfile("user@naver.com");
//        Assertions.assertThat(show.getUser()).isEqualTo(userJpaRepository.findByUserEmail("user@naver.com"));
//    }
}