package org.platanus.platachat.web.room.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RoomErrorResponse {
    /**
     * 에러 유니크 코드 Long
     */
    private long errorId;

    /**
     * 유니크 코드에 대응하는 일반명사
     */
    private String errorCode;

    /**
     * 상세 에러 메시지
     */
    private String errorMessage;
}
