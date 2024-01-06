package lab.spring.security.exception.sign;

import lab.spring.security.data.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class SignExceptionHandler {

    @ExceptionHandler(DuplicatedIdException.class)
    public ResponseEntity<ErrorResponse> handleDuplicatedIdException(DuplicatedIdException e) {
        ErrorResponse response = ErrorResponse.builder()
                .code(e.getErrorCode().getHttpStatus().value())
                .msg(e.getErrorCode().getMessage())
                .build();
        return ResponseEntity.status(e.getErrorCode().getHttpStatus()).body(response);
    }
}
