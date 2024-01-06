package lab.spring.security.controller;

import lab.spring.security.data.Member;
import lab.spring.security.data.dto.LoginDto;
import lab.spring.security.data.dto.MemberInfoDto;
import lab.spring.security.data.dto.MemberJoinDto;
import lab.spring.security.data.dto.TokenInfo;
import lab.spring.security.service.MemberService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;

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
