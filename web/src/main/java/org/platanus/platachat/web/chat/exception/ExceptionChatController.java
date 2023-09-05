package org.platanus.platachat.web.chat.exception;

import lombok.extern.slf4j.Slf4j;
import org.platanus.platachat.web.auth.dto.AuthValidRetrieveResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice({"org.platanus.platachat.web.chat.rest", "org.platanus.platachat.web.chat.web"})
public class ExceptionChatController {

    @ExceptionHandler(CustomChatException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public AuthValidRetrieveResponse authException(CustomChatException e) {
        log.error("AuthExceptionHandler", e);
        return e.getResponseDto();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public AuthValidRetrieveResponse authException(Exception e) {
        log.error("AuthExceptionHandler", e);
        CustomChatException res = (CustomChatException) e;
        return res.getResponseDto();
    }
}
