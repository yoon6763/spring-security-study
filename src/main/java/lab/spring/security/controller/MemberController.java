package lab.spring.security.controller;

import lab.spring.security.data.Member;
import lab.spring.security.data.dto.LoginDto;
import lab.spring.security.data.dto.MemberInfoDto;
import lab.spring.security.data.dto.MemberJoinDto;
import lab.spring.security.data.dto.TokenInfo;
import lab.spring.security.service.MemberService;
import lab.spring.security.service.OAuthService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;
    private final OAuthService oAuthService;

//    @GetMapping("/google")
//    public ResponseEntity<String> googleLogin(@RequestParam String code) {
//        return ResponseEntity.ok(oAuthService.getGoogleAccessToken(code).getBody());
//    }

    @GetMapping("/test")
    public ResponseEntity<String> test(@AuthenticationPrincipal OAuth2User principal) {
        if (principal == null) {
            log.info("principal is null");
        } else {
            log.info("principal = " + principal);
        }
        log.info("principal = " + principal);

        return ResponseEntity.ok("test");
    }

    @PostMapping("/login")
    public ResponseEntity<TokenInfo> login(@RequestBody LoginDto loginDto) {
        return ResponseEntity.ok(memberService.login(loginDto));
    }

    @PostMapping("/join")
    public ResponseEntity<MemberInfoDto> join(@RequestBody MemberJoinDto memberJoinDto) {
        return ResponseEntity.ok(memberService.join(memberJoinDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberInfoDto> getMemberInfo(@PathVariable String id) {
        return ResponseEntity.ok(memberService.getMemberInfo(id));
    }


}
