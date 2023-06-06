package com.whomeow.whomeow.user;

import com.whomeow.whomeow.exception.UserException;
import com.whomeow.whomeow.session.SessionConst;
import com.whomeow.whomeow.user.Dto.FindEmailRequestDto;
import com.whomeow.whomeow.user.Dto.SignInRequestDto;
import com.whomeow.whomeow.user.Dto.SignUpRequestDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@Slf4j
@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final KaKaoApi KaKaoApi;

    @RequestMapping(value = "/sign-up", method = RequestMethod.POST)
    public ResponseEntity<HashMap<String, String>> signUp(@RequestBody SignUpRequestDto signUpRequestDto) {
        User user = userService.signUp(signUpRequestDto);
        HashMap<String, String> map = new HashMap<>();
        /*
        if() {
            bindingResult.reject("signUpFail", "E-mail 형태로 입력해주세요.");
            return "E-mail 형태로 입력해주세요.";
        }
        */

        if(!signUpRequestDto.isPolicyAgreement()){
            map.put("error message", "약관에 동의해주세요.");
            return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
        }
        /*
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>("", HttpStatus.BAD_REQUEST);
        }
        */
        map.put("email", user.getUserEmail());
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @PostMapping(value="/sign-in")
    public ResponseEntity<HashMap<String, String>> signIn(@RequestBody SignInRequestDto signInRequestDto, HttpServletRequest request, BindingResult bindingResult) throws UserException {
        log.info("받은 email : " + signInRequestDto.getUserEmail());
        log.info("받은 password : " + signInRequestDto.getUserPassword());

        HashMap<String, String> map = new HashMap<>();

        User user = userService.signIn(signInRequestDto);
        if(user == null) {
            map.put("error message", "아이디 또는 비밀번호가 틀렸습니다.");
            return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
        }

        map.put("email", user.getUserEmail());

        //로그인 성공 처리
        //세션이 있으면 있는 세션을 반환, 없으면 신규 세션을 생성
        HttpSession session = request.getSession();
        //세션에 로그인 회원 정보 보관
        session.setAttribute(SessionConst.LOGIN_USER, user);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @RequestMapping("/kakaoSignIn")
    public ResponseEntity<HashMap<String, Object>> KaKaoSignIn(@RequestParam("code") String code, HttpServletRequest request) {
        log.info("code : "+ code);

        String accessToken = KaKaoApi.getAccessToken(code);
        log.info("accessToken : " + accessToken);

        HashMap<String, Object> userInfo = KaKaoApi.getUserInfo(accessToken);
        log.info("signin controller : " + userInfo);

        HashMap<String, Object> map = new HashMap<>();
        // 클라이언트의 이메일이 존재할 때 세션에 해당 이메일과 토큰을 등록
        if(userInfo.get("email") != null) {
            HttpSession session = request.getSession();
            session.setAttribute("userEmail", userInfo.get("email"));
            session.setAttribute("access_Token", accessToken);
        }
        map.put("email", userInfo.get("email"));
        //Todo 이거 userInfo.get("email")이 어떤 형태로 들어올지 test를 못해서 일단 Object로 타입을 넣어둠. 바꿔야 할 수도..?
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @PostMapping(value="/sign-out")
    public ResponseEntity<Object> signOut(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if(session != null) {
            session.invalidate();
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value="/kakao-sign-out")
    public ResponseEntity<Object> KaKaoSignOut(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String access_Token = (String) session.getAttribute("access_Token");

        if(access_Token != null && !"".equals(access_Token)) {
            KaKaoApi.kakaoSignOut(access_Token);
            session.removeAttribute("access_Token");
            session.removeAttribute("userEmail");
        } else {
            log.info("access_Token is null");
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/account/findEmail")
    public ResponseEntity<HashMap<String, String>> findEmail(FindEmailRequestDto findEmailRequestDto) {
        HashMap<String, String> map = new HashMap<>();

        String email = userService.findEmail(findEmailRequestDto);

        if (email == null || email.equals("")) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        map.put("email", email);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @RequestMapping(value = "/account/sendMail")
    public void sendApprovalKey(String userEmail) {
        userService.sendMail(userEmail);
    }

    @RequestMapping(value = "/account/confirmKey")
    public ResponseEntity<HashMap<String, String>> confirmKey(String userEmail, String ApprovalKey){
        HashMap<String, String> map = new HashMap<>();

        if(userService.confirmKey(userEmail, ApprovalKey)) {
            map.put("email", userEmail);
            return new ResponseEntity<>(map, HttpStatus.OK);
        }else {
            map.put("error message", "인증 키가 틀립니다.");
            return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/account/resetPassword")
    public ResponseEntity<HashMap<String, String>> resetPassword(@RequestParam("userEmail") String userEmail,
                                                                 @RequestParam("newPassword") String newPassword,
                                                                 @RequestParam("confirmPassword") String confirmPassword) {
        HashMap<String, String> map = new HashMap<>();

        if (userService.resetPassword(userEmail, newPassword, confirmPassword)) {
            map.put("email", userEmail);
            return new ResponseEntity<>(map, HttpStatus.OK);
        } else{
            map.put("error message", "비밀번호 초기화에 실패하였습니다.");
            return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
        }
    }
}
