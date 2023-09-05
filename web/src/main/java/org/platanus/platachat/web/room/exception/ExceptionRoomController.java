package org.platanus.platachat.web.room.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice("org.platanus.platachat.web.room.rest")
public class ExceptionRoomController {

    @ExceptionHandler(RoomException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public RoomErrorResponse roomException(Exception e) {
        log.error("RestExceptionHandler", e);
        return RoomErrorResponse.builder()
            .errorId(999L)
            .errorCode("ERROR")
            .errorMessage(e.getMessage())
            .build();
    }
}
