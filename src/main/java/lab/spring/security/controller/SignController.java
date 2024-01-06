package lab.spring.security.controller;

import lab.spring.security.data.dto.SignInRequestDto;
import lab.spring.security.data.dto.SignInResultDto;
import lab.spring.security.data.dto.SignUpRequestDto;
import lab.spring.security.data.dto.SignUpResultDto;
import lab.spring.security.service.SignService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/sign-api")
public class SignController {

    private final SignService signService;

    @PostMapping(value = "/sign-in")
    public SignInResultDto signIn(@RequestBody SignInRequestDto signInRequestDto) throws RuntimeException {

        SignInResultDto signInResultDto = signService.signIn(signInRequestDto);

        if (signInResultDto.getCode() == 0) {
            log.info("[signIn] 정상적으로 로그인되었습니다. id : {}, token : {}", signInRequestDto.getId(),
                    signInResultDto.getToken());
        }
        return signInResultDto;
    }

    @PostMapping(value = "/sign-up")
    public ResponseEntity<SignUpResultDto> signUp(@RequestBody SignUpRequestDto signUpRequestDto) {

        SignUpResultDto signUpResultDto = signService.signUp(signUpRequestDto);

        log.info("[signUp] 회원가입을 완료했습니다. id : {}", signUpRequestDto.getId());
        log.info("[signUp] 회원가입 결과 : {}", signUpResultDto);
        return new ResponseEntity<>(signUpResultDto, HttpStatus.OK);
    }

    @GetMapping(value = "/exception")
    public void exceptionTest() throws RuntimeException {
        throw new RuntimeException("접근이 금지되었습니다.");
    }

//    @ExceptionHandler(value = RuntimeException.class)
//    public ResponseEntity<Map<String, String>> ExceptionHandler(RuntimeException e) {
//        HttpHeaders responseHeaders = new HttpHeaders();
//        //responseHeaders.add(HttpHeaders.CONTENT_TYPE, "application/json");
//        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
//
//        log.error("ExceptionHandler 호출, {}, {}", e.getCause(), e.getMessage());
//
//        Map<String, String> map = new HashMap<>();
//        map.put("error type", httpStatus.getReasonPhrase());
//        map.put("code", "400");
//        map.put("message", "에러 발생");
//
//        return new ResponseEntity<>(map, responseHeaders, httpStatus);
//    }

}