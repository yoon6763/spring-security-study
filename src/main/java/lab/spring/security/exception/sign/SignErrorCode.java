package lab.spring.security.exception.sign;

import lab.spring.security.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SignErrorCode implements ErrorCode {

    DUPLICATED_ID(HttpStatus.BAD_REQUEST, "이미 사용중인 아이디입니다.");

    private final HttpStatus httpStatus;
    private final String message;

}
