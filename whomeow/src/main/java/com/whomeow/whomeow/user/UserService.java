package com.whomeow.whomeow.user;

import com.whomeow.whomeow.exception.UserException;
import com.whomeow.whomeow.user.Dto.FindEmailRequestDto;
import com.whomeow.whomeow.user.Dto.SignInRequestDto;
import com.whomeow.whomeow.user.Dto.SignUpRequestDto;
import com.whomeow.whomeow.user.Dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.mail.HtmlEmail;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Random;

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

    public String resetApprovalKey() {
        StringBuilder key = new StringBuilder();
        Random rd = new Random();

        for (int i = 0; i < 8; i++) {
            key.append(rd.nextInt(10));
        }

        return key.toString();
    }

    public User signUp(SignUpRequestDto signUpRequestDto){
        String userEmail = signUpRequestDto.getUserEmail();
        if (userJpaRepository.findByUserEmail(userEmail) == null) {
            String key = resetApprovalKey();

            UserDto userDto = UserDto
                    .builder()
                    .userEmail(signUpRequestDto.getUserEmail())
                    .userPassword(signUpRequestDto.getUserPassword())
                    .userName(signUpRequestDto.getUserName())
                    .phoneNumber(signUpRequestDto.getPhoneNumber())
                    .approvalKey(key)
                    .build();

            Date now = new Date();
            userDto.setCreateDate(now);
            userDto.setUpdateDate(now);

            userDto.setUserWithdraw(0);

            User user = userDto.toEntity();
            log.info("date : {}" , userDto.getCreateDate());

            return userJpaRepository.save(user);
        }
        log.info("중복된 이메일 발견!");
        return null;
    }

    public String findEmail(FindEmailRequestDto findEmailRequestDto) {
        return userJpaRepository.findEmailByNameAndPhoneNumber(findEmailRequestDto.getUserName(), findEmailRequestDto.getPhoneNumber());
    }

    public void sendMail(String userEmail) {
        User user = userJpaRepository.findByUserEmail(userEmail);
        if(user == null){
            return;
        }

        //Mail Server 설정
        String charSet = "utf-8";
        String hostSMTP = "smtp.naver.com";
        String hostSMTPid = "gmlakd4142@naver.com";
        String hostSMTPpwd = "ghd4142";

        //보내는 사람 Email, 제목, 내용
        String fromEmail = "gmlakd4142@naver.com";
        String fromName = "Whoyaong";
        String subject = "";
        String msg = "";

        //메일 본문
        subject = "Whoyaong 비밀번호 변경 인증번호 요청 메일입니다.";
        msg += "<div align='center' style='border:1px solid black; font-family:verdana'>";
        msg += "<h3 style='color: blue;'>";
        msg += user.getUserName() + "님 안녕하십니까.</h3>";
        msg += "<div style='font-size: 130%'>";
        msg += "하단의 인증 번호를 확인하시고 인증 화면에 입력해주세요.</div><br/>";
        msg += "<div>" + user.getApprovalKey() + "</div>";

        //받는 사람 E-Mail 주소
        String mail = user.getUserEmail();
        try {
            HtmlEmail email = new HtmlEmail();
            email.setDebug(true);
            email.setCharset(charSet);
            email.setSSL(true);
            email.setHostName(hostSMTP);
            email.setSmtpPort(587);

            email.setAuthentication(hostSMTPid, hostSMTPpwd);
            email.setTLS(true);
            email.addTo(mail, charSet);
            email.setFrom(fromEmail, fromName, charSet);
            email.setSubject(subject);
            email.setHtmlMsg(msg);
            email.send();
        } catch (Exception e) {
            System.out.println("메일발송 실패 : " + e);
        }
    }

    public boolean confirmKey(String userEmail, String approvalKey) {
        User user = userJpaRepository.findByUserEmail(userEmail);
        if(user == null)
            return false;
        else return user.getApprovalKey().equals(approvalKey);
    }

    @Transactional
    public boolean resetPassword(String userEmail, String newPassword, String confirmPassword){
        if(newPassword.equals(confirmPassword)){
            User user = userJpaRepository.findByUserEmail(userEmail);
            String password = user.updatePassword(newPassword);
            return true;
        }
        return false;
    }

}
