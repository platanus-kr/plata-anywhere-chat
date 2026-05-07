package org.platanus.platachat.web.room.exception;

public class RoomException extends RuntimeException {

    private String message;

    public RoomException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
