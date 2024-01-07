package lab.spring.security.service;

import lab.spring.security.config.security.JwtTokenProvider;
import lab.spring.security.data.CommonResponse;
import lab.spring.security.data.User;
import lab.spring.security.data.dto.SignInRequestDto;
import lab.spring.security.data.dto.SignInResultDto;
import lab.spring.security.data.dto.SignUpRequestDto;
import lab.spring.security.data.dto.SignUpResultDto;
import lab.spring.security.exception.ErrorCode;
import lab.spring.security.exception.sign.DuplicatedIdException;
import lab.spring.security.exception.sign.SignErrorCode;
import lab.spring.security.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Slf4j
@Service
public class SignService {

    public UserRepository userRepository;
    public JwtTokenProvider jwtTokenProvider;
    public PasswordEncoder passwordEncoder;

    @Autowired
    public SignService(UserRepository userRepository, JwtTokenProvider jwtTokenProvider,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
    }

    public SignUpResultDto signUp(SignUpRequestDto signUpRequestDto) {
        log.info("[getSignUpResult] 회원 가입 정보 전달");

        checkDuplicateId(signUpRequestDto.getId());

//        if (signInRequestDto.grole.equalsIgnoreCase("admin")) {
//            user = User.builder()
//                    .uid(id)
//                    .name(name)
//                    .password(passwordEncoder.encode(password))
//                    .roles(Collections.singletonList("ROLE_ADMIN"))
//                    .build();
//        } else {
//            user = User.builder()
//                    .uid(id)
//                    .name(name)
//                    .password(passwordEncoder.encode(password))
//                    .roles(Collections.singletonList("ROLE_USER"))
//                    .build();
//        }

        User user = User.builder()
                .uid(signUpRequestDto.getId())
                .name(signUpRequestDto.getName())
                .password(passwordEncoder.encode(signUpRequestDto.getPassword()))
                .roles(Collections.singletonList("ROLE_USER"))
                .build();

        User savedUser = userRepository.save(user);
        SignUpResultDto signUpResultDto = new SignUpResultDto();

        log.info("[getSignUpResult] userEntity 값이 들어왔는지 확인 후 결과값 주입");
        if (!savedUser.getName().isEmpty()) {
            log.info("[getSignUpResult] 정상 처리 완료");
            setSuccessResult(signUpResultDto);
        } else {
            log.info("[getSignUpResult] 실패 처리 완료");
            setFailResult(signUpResultDto);
        }
        return signUpResultDto;
    }

    private void checkDuplicateId(String id) {
        Optional<User> user = userRepository.getByUid(id);
        if (user.isPresent()) {
            throw new DuplicatedIdException();
        }
    }

    public SignInResultDto signIn(SignInRequestDto signInRequestDto) throws RuntimeException {

        String id = signInRequestDto.getId();
        String password = signInRequestDto.getPassword();

        log.info("[getSignInResult] signDataHandler 로 회원 정보 요청");
        User user = userRepository.getByUid(id).orElseThrow(() -> new BadCredentialsException("존재하지 않는 아이디입니다."));
        log.info("[getSignInResult] Id : {}", id);

        log.info("[getSignInResult] 패스워드 비교 수행");
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException();
        }
        log.info("[getSignInResult] 패스워드 일치");

        log.info("[getSignInResult] SignInResultDto 객체 생성");
        SignInResultDto signInResultDto = SignInResultDto.builder()
                .token(jwtTokenProvider.createToken(String.valueOf(user.getUid()),
                        user.getRoles()))
                .build();

        log.info("[getSignInResult] SignInResultDto 객체에 값 주입");
        setSuccessResult(signInResultDto);

        return signInResultDto;
    }

    public User getUserFromToken(String token) throws BadRequestException {
        log.info("[getUserFromToken] 토큰에서 회원 정보 추출");
        User user = userRepository.getByUid(jwtTokenProvider.getUsername(token))
                .orElseThrow(() -> new BadRequestException("존재하지 않는 회원입니다."));
        log.info("[getUserFromToken] 회원 정보 추출 완료 : {}", user);
        return userRepository.getByUid(jwtTokenProvider.getUsername(token))
                .orElseThrow(() -> new BadRequestException("존재하지 않는 회원입니다."));
    }

    // 결과 모델에 api 요청 성공 데이터를 세팅해주는 메소드
    private void setSuccessResult(SignUpResultDto result) {
        result.setSuccess(true);
        result.setCode(CommonResponse.SUCCESS.getCode());
        result.setMsg(CommonResponse.SUCCESS.getMsg());
    }

    // 결과 모델에 api 요청 실패 데이터를 세팅해주는 메소드
    private void setFailResult(SignUpResultDto result) {
        result.setSuccess(false);
        result.setCode(CommonResponse.FAIL.getCode());
        result.setMsg(CommonResponse.FAIL.getMsg());
    }
}