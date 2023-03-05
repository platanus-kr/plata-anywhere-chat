package org.platanus.platachat.web.AuthenticationTest;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.platanus.platachat.web.auth.dto.SessionMemberDto;
import org.platanus.platachat.web.member.dto.MemberLoginRequestDto;
import org.platanus.platachat.web.member.model.Member;
import org.platanus.platachat.web.auth.rest.AuthRestControllerV1;
import org.platanus.platachat.web.member.service.MemberService;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.authentication.AuthenticationManager;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
public class AppLoginTest {

    @Mock
    private MemberService memberService;

    /* authenticationManager : 로그인 시 토큰 처리용. */
    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthRestControllerV1 appAuthController;

    @Test
    public void 앱_로그인_세션발급_테스트() {
        // given
        final String TEST_USERNAME = "testUser";
        final String TEST_PASSWORD = "password";
        MemberLoginRequestDto dto = MemberLoginRequestDto.builder()
                .username(TEST_USERNAME)
                .password(TEST_PASSWORD)
                .build();
        MockHttpServletRequest mockRequest = new MockHttpServletRequest();
        MockHttpSession mockSession = new MockHttpSession();
        // JSESSIONID(aka PACSESSIONID) 임의로 지정해서 세션 생성되서 내려오는지 확인하는 용도. 중요!
        mockSession.setAttribute("PACSESSION", mockSession.changeSessionId());

        // when
        when(memberService.login(TEST_USERNAME, TEST_PASSWORD))
                .thenReturn(Member.builder().username(TEST_USERNAME).password(TEST_PASSWORD).build());

        // then
        SessionMemberDto sessionMemberDto = appAuthController.appLogin(dto, mockRequest, mockSession);
        System.out.println(sessionMemberDto.toString());
        assertThat(sessionMemberDto.getToken()).isNotBlank();
    }
}
