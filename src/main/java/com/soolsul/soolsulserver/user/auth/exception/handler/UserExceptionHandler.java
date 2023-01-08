package com.soolsul.soolsulserver.user.auth.exception.handler;

import com.soolsul.soolsulserver.common.response.BaseResponse;
import com.soolsul.soolsulserver.user.auth.exception.UserAlreadyExistsException;
import com.soolsul.soolsulserver.user.auth.exception.UserNicknameDuplicatedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.soolsul.soolsulserver.common.response.ResponseCodes.USER_DUPLICATED;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class UserExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<BaseResponse<Void>> duplicatedUser(UserAlreadyExistsException exception) {
        log.info("[UserAlreadyExistsException] Handler calling");
        BaseResponse<Void> baseResponse = new BaseResponse<>(USER_DUPLICATED.getCode(), exception.getMessage(), null);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(baseResponse);
    }

    @ExceptionHandler
    public ResponseEntity<BaseResponse<Void>> duplicatedNickname(UserNicknameDuplicatedException exception) {
        log.info("[UserNicknameDuplicatedException] Handler calling");
        BaseResponse<Void> baseResponse = new BaseResponse<>(USER_DUPLICATED.getCode(), exception.getMessage(), null);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(baseResponse);
    }

}
