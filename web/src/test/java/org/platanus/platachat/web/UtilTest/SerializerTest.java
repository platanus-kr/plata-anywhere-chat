package org.platanus.platachat.web.UtilTest;


import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonParser;

public class SerializerTest {
	
	public static final String SERIALIZED_SECURITY_CONTEXT = "{\n" +
			"  \"authentication\": {\n" +
			"    \"authorities\": [\n" +
			"      \"ROLE_USER\"\n" +
			"    ],\n" +
			"    \"principal\": {\n" +
			"      \"id\": 1,\n" +
			"      \"provider\": \"web\",\n" +
			"      \"username\": \"test1\",\n" +
			"      \"password\": \"$2a$10$25dEwhHUnHxkHU6sAJ3nj.tSrrRiJ0uCBoDNEAPlTkqoe2kJV9d9S\",\n" +
			"      \"nickname\": \"xedx85x8cxecx8axa4xedx8axb81\",\n" +
			"      \"email\": \"test1@test.com\",\n" +
			"      \"deleted\": false,\n" +
			"      \"appRole\": \"ROLE_USER\",\n" +
			"      \"lastActivated\": \"2023-05-10T09:07:24.856034\"\n" +
			"    },\n" +
			"    \"authenticated\": true,\n" +
			"    \"name\": \"Member(super\\u003dorg.platanus.platachat.web.member.model.Member@76fb72bc, id\\u003d1, providerId\\u003dnull, provider\\u003dweb, username\\u003dtest1, password\\u003d$2a$10$25dEwhHUnHxkHU6sAJ3nj.tSrrRiJ0uCBoDNEAPlTkqoe2kJV9d9S, nickname\\u003dxedx85x8cxecx8axa4xedx8axb81, profileImage\\u003dnull, htmlUrl\\u003dnull, email\\u003dtest1@test.com, deleted\\u003dfalse, appRole\\u003dROLE_USER, lastActivated\\u003d2023-05-10T09:07:24.856034)\"\n" +
			"  }\n" +
			"}";
	
	@Test
	void 역직렬화기_테스트() {
		
		System.out.println(SERIALIZED_SECURITY_CONTEXT);
	}
}
