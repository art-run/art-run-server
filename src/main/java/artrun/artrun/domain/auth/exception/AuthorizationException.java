package artrun.artrun.domain.auth.exception;

import artrun.artrun.global.error.exception.BusinessException;
import artrun.artrun.global.error.exception.ErrorCode;

public class AuthorizationException extends BusinessException {
    public AuthorizationException(ErrorCode errorCode) {
        super(errorCode);
    }

    public AuthorizationException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}
