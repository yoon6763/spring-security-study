package lab.spring.security.exception.sign;

import lab.spring.security.exception.BasicException;
import lab.spring.security.exception.ErrorCode;
import lombok.Getter;

@Getter
public class DuplicatedIdException extends BasicException {

    private final ErrorCode errorCode;

    public DuplicatedIdException() {
        super(SignErrorCode.DUPLICATED_ID);
        this.errorCode = SignErrorCode.DUPLICATED_ID;
    }

}
