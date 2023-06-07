package org.platanus.platachat.web.constants;


public record RoomConstant() {
	public static final String ROOM_MEMBER_VALIDATE_FAILED_MESSAGE = "회원이 아닙니다";
	public static final String ROOM_NOT_FOUND_ROOM_MESSAGE = "존재하지 않는 채팅방입니다.";
	public static final String ROOM_NOT_OWNER_VALIDATE_FAILED_MESSAGE = "채팅방의 소유자만 변경할 수 있습니다.";
	public static final String ROOM_NOT_FOUND_ROOM_MEMBER_MESSAGE = "존재하지 않는 채팅방 사용자 입니다.";
	public static final String ROOM_NOT_EXIT_OWNER_MESSAGE = "방장은 채팅방에서 나갈 수 없습니다.";
	public static final String ROOM_ALREADY_JOIN_ROOM_MESSAGE = "이미 참여한 채팅방입니다.";
	public static final String ROOM_PRIVATE_ROOM_MESSAGE = "비공개 채팅방입니다.";
	public static final String ROOM_ALREADY_ENDED_ROOM_MESSAGE = "종료된 채팅방입니다.";
	public static final String ROOM_PARTICIPATES_OVER_MESSAGE = "채팅방 인원이 가득 찼습니다.";
	public static final String ROOM_EXIT_FAILED_MESSAGE = "채팅방에서 나가는데 실패했습니다.";
	public static final String ROOM_VALIDATE_ROOM_ID_IS_BLANK_MESSAGE = "채팅방 아이디는 필수입니다.";
	public static final String ROOM_VALIDATE_CAPACITY_IS_BLANK_MESSAGE = "채팅방 최대 인원은 필수입니다.";
	public static final String ROOM_VALIDATE_PUBLIC_IS_BLANK_MESSAGE = "채팅방 공개여부는 필수입니다.";
	public static final String ROOM_VALIDATE_STATUS_IS_BLANK_MESSAGE = "채팅방 상태는 필수입니다.";
	public static final String ROOM_VALIDATE_OWNER_IS_BLANK_MESSAGE = "채팅방 주인은 필수입니다.";
	public static final String ROOM_VALIDATE_ROOM_NAME_IS_BLANK_MESSAGE = "채팅방 이름은 필수입니다.";
    public static final String ROOM_INFORMATION_CHANGE_OK_MESSAGE = "변경이 완료 되었습니다.";
	public static final String ROOM_ALREADY_ROOM_OWNER_MESSAGE = "이미 방장입니다.";
	public static final String ROOM_END_CHAT_VALIDATE_OWNER_MESSAGE = "방장만 채팅방을 종료할 수 있습니다.";
}
