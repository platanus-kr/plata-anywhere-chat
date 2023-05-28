package org.platanus.platachat.web.room.repository.exception;

import lombok.Getter;

@Getter
public class RoomException extends RuntimeException {

    private String message;

    public RoomException(String message) {
        this.message = message;
    }
}
