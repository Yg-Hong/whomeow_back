package com.whomeow.whomeow.profile;

import com.whomeow.whomeow.user.User;
import com.whomeow.whomeow.user.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileJpaRepository profileJpaRepository;
    private final UserJpaRepository userJpaRepository;

    public Dog showProfile(String userEmail) {
        User user = userJpaRepository.findByUserEmail(userEmail);
        Optional<Dog> dog = profileJpaRepository.findByUser(user);
        return dog.orElseGet(dog::get);
    }

    /**
     * @param user 세션에서 꺼내온 user 로 profile 생성 및 수정
     * @return
     */
    public Dog editProfile(User user, DogDto dogDto) {
        log.info("서비스 진입 성공");
        Date now = new Date();

        if (profileJpaRepository.findByUser(user).isEmpty()) {
            log.info("서비스 :::::::::: dog 정보 없을 때");
            Dog dog = Dog.builder()
                    .dogPhoto(dogDto.getDogPhoto())
                    .dogName(dogDto.getDogName())
                    .dogAge(dogDto.getDogAge())
                    .dogSex(dogDto.getDogSex())
                    .dogWeight(dogDto.getDogWeight())
                    .dogBread(dogDto.getDogBread())
                    .user(user)
                    .createdAt(now)
                    .updatedAt(now)
                    .build();
            Dog save = profileJpaRepository.save(dogDto.toEntity());
            return save;
        } else {
            log.info("서비스 :::::::::: dog 정보 있을 때");
            Dog edit = profileJpaRepository.findByUser(user).get();
            edit.update(dogDto.getDogPhoto(), dogDto.getDogName(), dogDto.getDogAge(), dogDto.getDogSex(),
                    dogDto.getDogWeight(), dogDto.getDogBread(), now);
            Dog save = profileJpaRepository.save(edit);
            return save;
        }
    }
}
