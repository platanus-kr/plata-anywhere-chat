package org.platanus.platachat.web.auth.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LoginProvider {
	
	WEB("WEB", "앱 로그인"),
	GITHUB("GITHUB", "GitHub"),
	GOOGLE("GOOGLE", "Google"),
	KAKAO("KAKAO", "카카오"),
	NAVER("NAVER", "네이버");
	
	private final String key;
	private final String name;
}
