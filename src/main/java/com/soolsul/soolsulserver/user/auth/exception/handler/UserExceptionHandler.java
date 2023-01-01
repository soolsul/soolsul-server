package com.soolsul.soolsulserver.user.auth.exception.handler;

import com.soolsul.soolsulserver.common.response.BaseResponse;
import com.soolsul.soolsulserver.common.response.ResponseCodeAndMessages;
import com.soolsul.soolsulserver.user.auth.exception.UserAlreadyExistsException;
import com.soolsul.soolsulserver.user.auth.exception.UserInvalidIdException;
import com.soolsul.soolsulserver.user.auth.exception.UserNicknameDuplicatedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class UserExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<BaseResponse<Void>> duplicatedUser(UserAlreadyExistsException exception) {
        log.info("[UserAlreadyExistsException] Handler calling");
        return new ResponseEntity(new BaseResponse<>(ResponseCodeAndMessages.USER_DUPLICATED, null), HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    public ResponseEntity<BaseResponse<Void>> duplicatedNickname(UserNicknameDuplicatedException exception) {
        log.info("[UserNicknameDuplicatedException] Handler calling");
        return new ResponseEntity(new BaseResponse<>(ResponseCodeAndMessages.USER_DUPLICATED.getCode(), exception.getMessage(), null), HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    public ResponseEntity<BaseResponse<Void>> invalidId(UserInvalidIdException exception) {
        log.info("[UserInvalidIdException] Handler calling");
        return new ResponseEntity(new BaseResponse<>(ResponseCodeAndMessages.USER_UNAUTHENTICATED.getCode(), exception.getMessage(), null), HttpStatus.CONFLICT);
    }
}
