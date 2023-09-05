package org.platanus.platachat.web.auth.acceptance;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.platanus.platachat.web.member.MemberFixture;
import org.platanus.platachat.web.util.SpringBootTestPrefix;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;


public class LoginAcceptanceTest extends SpringBootTestPrefix {

    private static final String WEB_PROVIDER = "WEB";

    @DisplayName("어플리케이션 자체 로그인 테스트")
    @Test
    public void test() {
        MemberFixture.일반_A1_회원_생성();

        var response = AuthSteps.회원_로그인_API(MemberFixture.A1_ID, MemberFixture.A1_PASSWORD);

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());

        /**
         * {
         *     "id": "1cf4e67d-c33d-418b-8333-e831fd8828db",
         *     "provider": "WEB",
         *     "username": "TEST_A1",
         *     "profileImage": null,
         *     "htmlUrl": null,
         *     "nickname": "TEST A1",
         *     "email": "TEST.A1@email.com",
         *     "appRole": "ROLE_USER",
         *     "token": "542f1db8-3a35-46c8-ab0e-f8e5c3c72632"
         * }
         */
        String provider = response.jsonPath().getString("provider");
        String username = response.jsonPath().getString("username");

        assertThat(provider).isEqualTo(WEB_PROVIDER);
        assertThat(username).isEqualTo(MemberFixture.A1_ID);
    }

}
