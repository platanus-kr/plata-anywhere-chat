package org.platanus.platachat.web.room.exception;

public record RoomErrorResponse(
    /**
     * 에러 유니크 코드 Long
     */
    long errorId,

    /**
     * 유니크 코드에 대응하는 일반명사
     */
    String errorCode,

    /**
     * 상세 에러 메시지
     */
    String errorMessage
) {
}
