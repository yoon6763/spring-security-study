package lab.spring.security.controller;

import lab.spring.security.service.CustomOAuth2UserService;
import lab.spring.security.service.SocialLoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class OAuth2Controller {

    private final SocialLoginService socialLoginService;

    private final String GOOGLE_SNS_BASE_URL = "https://accounts.google.com/o/oauth2/v2/auth";
    private final String GOOGLE_SNS_CLIENT_ID = "375424656347-ki62a9rrh3m31i1fssrj9bk82horcncl.apps.googleusercontent.com";
    private final String GOOGLE_SNS_CALLBACK_URL = "http://127.0.0.1:8080/api/oauth2/callback/google";
    private final String GOOGLE_SNS_CALLBACK_URL2 = "http://127.0.0.1:8080/api/oauth2/google";

    private final String GOOGLE_SNS_CLIENT_SECRET = "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";
    private final String GOOGLE_SNS_TOKEN_BASE_URL = "https://oauth2.googleapis.com/token";

    @GetMapping("/api/oauth2/callback/google")
    public String callback(@RequestParam("code") String code) {
        log.info("code = {}", code);
        socialLoginService.socialLogin(code, "google");

        return "Success";
    }
}
