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
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;

@Slf4j
@Controller
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
    public ModelAndView signIn(@RequestBody SignInRequestDto signInRequestDto, HttpServletRequest request, BindingResult bindingResult) throws UserException {
        log.info("받은 email : " + signInRequestDto.getUserEmail());
        log.info("받은 password : " + signInRequestDto.getUserPassword());

        ModelAndView view = new ModelAndView();

        User user = userService.signIn(signInRequestDto);
        if(user == null) {
            throw new UserException("아이디 또는 비밀번호가 틀렸습니다.");
        }

        view.setViewName("/status");
        view.addObject("email", user.getUserEmail());

        //로그인 성공 처리
        //세션이 있으면 있는 세션을 반환, 없으면 신규 세션을 생성
        HttpSession session = request.getSession();
        //세션에 로그인 회원 정보 보관
        session.setAttribute(SessionConst.LOGIN_USER, user);

        return view;
    }

    @RequestMapping("/kakaoSignIn")
    public ModelAndView KaKaoSignIn(@RequestParam("code") String code, HttpServletRequest request) {
        log.info("code : "+ code);

        String accessToken = KaKaoApi.getAccessToken(code);
        log.info("accessToken : " + accessToken);

        HashMap<String, Object> userInfo = KaKaoApi.getUserInfo(accessToken);
        log.info("signin controller : " + userInfo);

        // 클라이언트의 이메일이 존재할 때 세션에 해당 이메일과 토큰을 등록
        if(userInfo.get("email") != null) {
            HttpSession session = request.getSession();
            session.setAttribute("userEmail", userInfo.get("email"));
            session.setAttribute("access_Token", accessToken);
        }

        ModelAndView view = new ModelAndView();
        view.setViewName("status");
        view.addObject("email", userInfo.get("email"));

        return view;
    }

    @PostMapping(value="/sign-out")
    public ModelAndView signOut(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if(session != null) {
            session.invalidate();
        }
        return new ModelAndView("login");
    }

    @RequestMapping(value="/kakao-sign-out")
    public ModelAndView KaKaoSignOut(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String access_Token = (String) session.getAttribute("access_Token");

        if(access_Token != null && !"".equals(access_Token)) {
            KaKaoApi.kakaoSignOut(access_Token);
            session.removeAttribute("access_Token");
            session.removeAttribute("userEmail");
        } else {
            log.info("access_Token is null");
        }

        return new ModelAndView("login");
    }

    @RequestMapping(value = "/account/findEmail")
    public ModelAndView findEmail(FindEmailRequestDto findEmailRequestDto) throws Exception{
        String email = userService.findEmail(findEmailRequestDto);

        if (email == null || email.equals("")) {
            throw new Exception("검색된 email이 없습니다.");
        }

        ModelAndView view = new ModelAndView();
        view.setViewName("confirmKey");
        view.addObject("email", email);


        return view;
    }

    @RequestMapping(value = "/account/sendMail")
    public void sendApprovalKey(String userEmail) {
        userService.sendMail(userEmail);
    }

    @RequestMapping(value = "/account/confirmKey")
    public ModelAndView confirmKey(String userEmail, String ApprovalKey) throws Exception{
        ModelAndView view = new ModelAndView();
        if(userService.confirmKey(userEmail, ApprovalKey)) {
            view.setViewName("resetPassword");
            view.addObject("userEmail", userEmail);

            return view;
        }else {
            throw new Exception("인증 키가 틀립니다.");
        }
    }

    @RequestMapping(value = "/account/resetPassword")
    public ModelAndView resetPassword(@RequestParam("userEmail") String userEmail,
                                      @RequestParam("newPassword") String newPassword,
                                      @RequestParam("confirmPassword") String confirmPassword) throws Exception {


        if (userService.resetPassword(userEmail, newPassword, confirmPassword)) {
            ModelAndView view = new ModelAndView();
            view.setViewName("login");
            view.addObject("userEmail", userEmail);

            return view;
        } else {
            throw new Exception("비밀번호 초기화에 실패하였습니다.");
        }
    }
}
