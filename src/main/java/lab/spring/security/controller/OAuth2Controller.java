package lab.spring.security.controller;

import jakarta.servlet.http.HttpServletResponse;
import lab.spring.security.data.dto.GoogleUserResourceDto;
import lab.spring.security.service.SocialLoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@RestController
@RequiredArgsConstructor
@Slf4j
public class OAuth2Controller {

    private final SocialLoginService socialLoginService;

    @GetMapping("/api/oauth2/callback/google")
    public void callback(@RequestParam("code") String code, HttpServletResponse response) throws IOException {
        log.info("code = {}", code);
        GoogleUserResourceDto googleUser = socialLoginService.socialLogin(code, "google");

        String email = URLEncoder.encode(googleUser.getEmail(), StandardCharsets.UTF_8);
        String name = URLEncoder.encode(googleUser.getName(), StandardCharsets.UTF_8);

        response.sendRedirect("http://localhost:3000/register?email=" + email + "&name=" + name);
    }

    @GetMapping("/api/v1/oauth2/user/google")
    public ResponseEntity<GoogleUserResourceDto> googleUserInfo(@RequestParam("code") String code) {
        log.info("code = {}", code);
        GoogleUserResourceDto google = socialLoginService.socialLogin(code, "google");
        return ResponseEntity.ok(google);
    }
}