package com.soolsul.soolsulserver.reply.exception.handler;

import com.soolsul.soolsulserver.common.response.BaseResponse;
import com.soolsul.soolsulserver.common.response.ResponseCodeAndMessages;
import com.soolsul.soolsulserver.reply.exception.ReplyNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ReplyExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<BaseResponse<Void>> replyNotFoundExceptionHandler(ReplyNotFoundException exception) {
        log.info("[ReplyNotFoundException] Handler calling");
        return ResponseEntity.ok(new BaseResponse<>(ResponseCodeAndMessages.REPLY_DELETE_SUCCESS, null));
    }
}
