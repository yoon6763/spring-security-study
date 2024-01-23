package lab.spring.security.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class OAuth2Controller {

    @GetMapping("/api/oauth2/callback/google")
    public String callback(@AuthenticationPrincipal OAuth2User oauth2User) {
        log.info("oauth2User: {}", oauth2User);
        return "콜백 페이지";
    }

}
