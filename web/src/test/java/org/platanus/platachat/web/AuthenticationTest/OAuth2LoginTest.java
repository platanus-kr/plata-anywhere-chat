package org.platanus.platachat.web.AuthenticationTest;

import org.junit.jupiter.api.Test;
import org.platanus.platachat.web.auth.CustomOAuth2UserService;
import org.platanus.platachat.web.front.web.TestController;
import org.platanus.platachat.web.member.model.AppRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oauth2Login;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = TestController.class)
@MockBeans({
        @MockBean(JpaMetamodelMappingContext.class), // JpaAuditing
        @MockBean(CustomOAuth2UserService.class)
})
public class OAuth2LoginTest {
    @Autowired
    private MockMvc mvc;

    @Test
    void OAuth2_인가_테스트() throws Exception {
        mvc.perform(get("/test/endpoint")
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
