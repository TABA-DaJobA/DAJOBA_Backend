package taba.dajoba.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import taba.dajoba.service.UserService;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * 회원가입회면 이동
     */
    @GetMapping("/signup")
    public String signUpPage(){
        return "/user/signup";
    }

    /**
     * 회원가입처리
     */
    @PostMapping("/signup")
    public String signUp(@ModelAttribute UserDTO userDTO){
        userService.join(userDTO);
        return "/user/login";
    }

    /**
     * 로그인화면 이동
     */
    @GetMapping("/login")
    public String loginPage(){
        return "/user/login";
    }

    /**
     * 로그인처리
     */
    @PostMapping("/login")
    public String login(@ModelAttribute UserDTO userDTO, HttpSession session){
        UserDTO loginResult = userService.login(userDTO);
        if(loginResult != null ){
            //로그인 성공
            session.setAttribute("loginUser", loginResult.getUserId());
            return "main";
        } else {
            return "/user/login";
        }
    }

    /**
     * 로그아웃처리
     */
    @PostMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "main";
    }



}
