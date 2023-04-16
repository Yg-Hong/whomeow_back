package com.whomeow.whomeow.user;

import com.whomeow.whomeow.exception.UserException;
import com.whomeow.whomeow.user.Dto.SignInRequestDto;
import com.whomeow.whomeow.user.Dto.SignUpRequestDto;
import com.whomeow.whomeow.user.Dto.UserDto;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserJpaRepository userJpaRepository;

    public SignInRequestDto signInCheck(SignInRequestDto signInRequestDto) throws UserException {
        User user = userJpaRepository.findByUserEmail(signInRequestDto.getUserEmail());
        if(user == null){
            //아이디가 존재하지 않을 경우
            throw new UserException(signInRequestDto.getUserEmail() + "란 아이디는 존재하지 않습니다.");
        }

        String password = user.getUserPassword();
        if(!password.equals(signInRequestDto.getUserPassword())) {
            throw new UserException("비밀번호가 일치하지 않습니다.");
        }

        return signInRequestDto;
    }
    
    public User signIn(SignInRequestDto signInRequestDto) throws UserException {
        SignInRequestDto requestDto = signInCheck(signInRequestDto);
        User user = userJpaRepository.findByUserEmail(requestDto.getUserEmail());

        log.info(user.getUserEmail());
        log.info(user.getUserName());
        log.info(user.getUserPassword());
        return user;
    }

    public String signOut(HttpSession session) {
        session.invalidate();
        return "/main";
    }

    public String signUp(SignUpRequestDto signUpRequestDto){
        String userEmail = signUpRequestDto.getUserEmail();
        if (userJpaRepository.findByUserEmail(userEmail) == null) {
            UserDto userDto = UserDto
                    .builder()
                    .userEmail(signUpRequestDto.getUserEmail())
                    .userPassword(signUpRequestDto.getUserPassword())
                    .userName(signUpRequestDto.getUserName())
                    .build();

            Date now = new Date();
            userDto.setCreateDate(now);
            userDto.setUpdateDate(now);

            userDto.setUserWithdraw(0);

            User user = userDto.toEntity();
            log.info("date : {}" , userDto.getCreateDate());

            userJpaRepository.save(user);
            return user.getUserEmail();
        }
        log.info("중복된 이메일 발견!");
        return null;
    }

}
