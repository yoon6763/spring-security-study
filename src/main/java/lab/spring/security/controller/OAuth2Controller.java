package lab.spring.security.controller;

import lab.spring.security.service.SocialLoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class OAuth2Controller {

    private final SocialLoginService socialLoginService;

    @GetMapping("/api/oauth2/callback/google")
    public String callback(@RequestParam("code") String code) {
        log.info("code = {}", code);
        socialLoginService.socialLogin(code, "google");

        return "Success";
    }

}
