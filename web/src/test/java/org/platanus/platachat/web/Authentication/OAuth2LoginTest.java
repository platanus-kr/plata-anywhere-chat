package org.platanus.platachat.web.Authentication;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oauth2Login;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.platanus.platachat.web.auth.dto.LoginProvider;
import org.platanus.platachat.web.auth.dto.SessionMemberDto;
import org.platanus.platachat.web.auth.oauth2.CustomOAuth2UserService;
import org.platanus.platachat.web.auth.rest.AuthRestControllerV1;
import org.platanus.platachat.web.member.model.AppRole;
import org.platanus.platachat.web.member.model.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@Deprecated(since = "이것도 테스트 다시 작성하기")
@WebMvcTest(controllers = AuthRestControllerV1.class)
@MockBeans({
        @MockBean(JpaMetamodelMappingContext.class), // JpaAuditing
        @MockBean(CustomOAuth2UserService.class)
})
public class OAuth2LoginTest {
    @Autowired
    private WebApplicationContext context;

    @Test
    void OAuth2_인가_테스트() throws Exception {
        Member m = Member.builder()
                .username("testUser")
                .nickname("githubOAuth2User")
                .email("email@gmail.com")
                .provider(LoginProvider.GITHUB)
                .build();
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("member", new SessionMemberDto(m, "TEST_SESSION_ID"));
        MockMvc mvc = MockMvcBuilders.webAppContextSetup(context).build();

        mvc.perform(get("/api/v1/auth/endpoint/check")
                        .session(session)
                        .with(oauth2Login()
                                .authorities(new SimpleGrantedAuthority(AppRole.ROLE_USER.getKey()))
                                .attributes(attr -> {
                                    attr.put("username", "testUser");
                                    attr.put("nickname", "githubOAuth2User");
                                    attr.put("email", "email@gmail.com");
                                    attr.put("provider", "github");
                                })
                        ))
                .andExpect(status().isOk());
    }
}
