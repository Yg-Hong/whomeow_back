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

    @PostMapping(value = "/sign-up", consumes = "application/json")
    @ResponseBody
    public String signUp(@RequestBody SignUpRequestDto signUpRequestDto, BindingResult bindingResult) {
        User user = userService.signUp(signUpRequestDto);
        /*
        if() {
            bindingResult.reject("signUpFail", "E-mail 형태로 입력해주세요.");
            return "E-mail 형태로 입력해주세요.";
        }
        */
        if(!signUpRequestDto.isPolicyAgreement()){
            bindingResult.reject("signUpFail", "약관에 동의해주세요.");
            return "약관에 동의해주세요.";
        }
        if (bindingResult.hasErrors()) {
            return null;
        }

        return user.getUserEmail();
    }

    @PostMapping(value="/sign-in")
    @ResponseBody
    public String signIn(@RequestBody SignInRequestDto signInRequestDto, HttpServletRequest request, BindingResult bindingResult) throws UserException {
        log.info("받은 email : " + signInRequestDto.getUserEmail());
        log.info("받은 password : " + signInRequestDto.getUserPassword());
        if (bindingResult.hasErrors()) {
            return null;
        }

        User user = userService.signIn(signInRequestDto);
        if(user == null) {
            bindingResult.reject("signInFail", "아이디 또는 비밀번호가 틀렸습니다.");
            return "아이디 또는 비밀번호가 틀렸습니다.";
        }
        //로그인 성공 처리
        //세션이 있으면 있는 세션을 반환, 없으면 신규 세션을 생성
        HttpSession session = request.getSession();
        //세션에 로그인 회원 정보 보관
        session.setAttribute(SessionConst.LOGIN_USER, user);
        return "/loginHome";
    }

    @RequestMapping("/kakaoSignIn")
    public String KaKaoSignIn(@RequestParam("code") String code, HttpServletRequest request) {
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
        return "/loginHome";
    }

    @PostMapping(value="/sign-out")
    public String signOut(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if(session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }

    @RequestMapping(value="/kakao-sign-out")
    public String KaKaoSignOut(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String access_Token = (String) session.getAttribute("access_Token");

        if(access_Token != null && !"".equals(access_Token)) {
            KaKaoApi.kakaoSignOut(access_Token);
            session.removeAttribute("access_Token");
            session.removeAttribute("userEmail");
        } else {
            log.info("access_Token is null");
        }

        return "main";
    }

    @RequestMapping(value = "/account/findEmail")
    public ModelAndView findEmail(FindEmailRequestDto findEmailRequestDto) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/account/findEmail");

        String email = userService.findEmail(findEmailRequestDto);
        mv.addObject("Email", email);
        return mv;
    }

    @RequestMapping(value = "/account/sendMail")
    public void sendApprovalKey(String userEmail) {
        userService.sendMail(userEmail);
    }

    @RequestMapping(value = "/account/confirmKey")
    public String confirmKey(String userEmail, String ApprovalKey){
        if(userService.confirmKey(userEmail, ApprovalKey))
            return "/account/resetPassword?userEmail=" + userEmail;
        else {
            return "redirect:/";
        }
    }

    @RequestMapping(value = "/account/resetPassword")
    public String resetPassword(String userEmail, String newPassword, String confirmPassword) {
        userService.resetPassword(userEmail, newPassword, confirmPassword);
        return "/main";
    }
}
