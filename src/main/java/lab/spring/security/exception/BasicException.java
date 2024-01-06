package lab.spring.security.exception;

import lombok.Getter;

@Getter
public abstract class BasicException extends RuntimeException {

    private final ErrorCode errorCode;

    public BasicException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
