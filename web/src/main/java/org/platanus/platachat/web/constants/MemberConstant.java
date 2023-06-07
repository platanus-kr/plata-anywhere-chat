package org.platanus.platachat.web.constants;
public record MemberConstant() {
	
	public static final String MEMBER_DUPLICATED_EMAIL_MESSAGE = "중복된 이메일 입니다.";
	public static final String MEMBER_DUPLICATED_USERID_MESSAGE = "중복된 아이디 입니다.";
	public static final String MEMBER_DUPLICATED_NICKNAME_MESSAGE = "중복된 닉네임 입니다.";
	public static final String MEMBER_ALREADY_REVOKE_USER_MESSAGE = "이미 탈퇴한 회원입니다.";
	public static final String MEMBER_NOT_FOUND_MESSAGE = "없는 회원 입니다.";
	public static final String MEMBER_NOT_MATCH_PASSWORD_MESSAGE = "패스워드가 일치하지 않습니다";
}
