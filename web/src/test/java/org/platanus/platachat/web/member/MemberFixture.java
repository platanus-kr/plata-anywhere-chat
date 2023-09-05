package org.platanus.platachat.web.member;

@SuppressWarnings("NonAsciiCharacters")
public class MemberFixture {
    public static final String A1_ID = "TEST_A1";
    public static final String A1_PASSWORD = "A1PASSWORD";
    public static String 일반_A1_회원_생성() {
        var response = MemberSteps.회원_생성_API(A1_ID, A1_PASSWORD, "TEST A1", "TEST.A1@email.com");
        return response.jsonPath().get("username");
    }
}
