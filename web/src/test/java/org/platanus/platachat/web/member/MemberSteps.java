package org.platanus.platachat.web.member;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.springframework.http.MediaType;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("NonAsciiCharacters")
public class MemberSteps {
    public static ExtractableResponse<Response> 회원_생성_API(final String username,
                                                          final String password,
                                                          final String nickname,
                                                          final String email) {
        Map<String, String> params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);
        params.put("nickname", nickname);
        params.put("email", email);

        /**
         * {
         *   "id": "a7a890a2-5235-499a-950a-2a3564ce14b0",
         *   "username": "test312",
         *   "nickname": "이름312",
         *   "email": "d312@d.com"
         * }
         */
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(params)
                .when().post("/api/v1/auth/join")
                .then().log().all().extract();
    }
}
