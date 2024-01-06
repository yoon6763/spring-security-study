package lab.spring.security.service;

import lab.spring.security.data.Member;
import lab.spring.security.data.dto.LoginDto;
import lab.spring.security.data.dto.MemberInfoDto;
import lab.spring.security.data.dto.MemberJoinDto;
import lab.spring.security.data.dto.TokenInfo;
import lab.spring.security.repository.MemberRepository;
import lab.spring.security.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public TokenInfo login(LoginDto loginDto) {
        log.info("memberService loginDto: {}", loginDto);

        String id = loginDto.getId();
        String password = loginDto.getPassword();

        // 1. Login ID/PW 를 기반으로 Authentication 객체 생성
        // 이때 authentication 는 인증 여부를 확인하는 authenticated 값이 false
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(id, password);

        // 2. 실제 검증 (사용자 비밀번호 체크)이 이루어지는 부분
        // authenticate 매서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드가 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);

        return tokenInfo;
    }

    @Transactional
    public MemberInfoDto join(MemberJoinDto memberJoinDto) {
        Member member = memberJoinDto.toEntity();

        checkDuplicatedId(member);
        checkDuplicatedNickname(member);
        return MemberInfoDto.of(memberRepository.save(member));
    }

    // duplicated id check
    private void checkDuplicatedId(Member member) {
        memberRepository
                .findById(member.getId())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    // duplicated nickname check
    private void checkDuplicatedNickname(Member member) {
        memberRepository
                .findByNickname(member.getNickname())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 닉네임입니다.");
                });
    }

}
