package artrun.artrun.domain.auth.exception;

import artrun.artrun.global.error.exception.BusinessException;
import artrun.artrun.global.error.exception.ErrorCode;


public class AuthenticationException extends BusinessException {

    public AuthenticationException(ErrorCode errorCode) {
        super(errorCode);
    }
}
