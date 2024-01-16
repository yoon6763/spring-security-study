package lab.spring.security.controller;

import lab.spring.security.service.OAuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/oauth2")
@RestController
@Slf4j
public class LoginController {

    private final OAuthService oAuthService;

    @GetMapping("callback/google")
    public ResponseEntity<String> successGoogleLogin(@RequestParam("code") String accessCode){
        log.info("accessCode = " + accessCode);
        return oAuthService.getGoogleAccessToken(accessCode);
    }

}
