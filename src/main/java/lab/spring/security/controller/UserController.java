package lab.spring.security.controller;

import lab.spring.security.data.dto.UserSignUpDto;
import lab.spring.security.jwt.service.JwtService;
import lab.spring.security.service.CustomOAuth2UserService;
import lab.spring.security.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;
    private final JwtService jwtService;
    private final CustomOAuth2UserService customOAuth2UserService;

    @PostMapping("/sign-up")
    public String signUp(@RequestBody UserSignUpDto userSignUpDto) throws Exception {
        userService.signUp(userSignUpDto);
        return "회원가입 성공";
    }

    @GetMapping("/jwt-test")
    public String jwtTest() {
        return "jwtTest 요청 성공";
    }

    @GetMapping("/test")
    public String test() {
        log.info("test() 실행");
        return "test";
    }

    @GetMapping("/api/oauth2/callback/google")
    public String googleLoginTest(@AuthenticationPrincipal OAuth2User oAuth2User) {
        log.info("googleLoginTest() 실행");
        log.info("oAuth2User: {}", oAuth2User);
        return "google login test";
    }
}