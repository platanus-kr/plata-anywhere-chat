package org.platanus.platachat.web.chat.web;

import org.junit.jupiter.api.Test;
import org.platanus.platachat.web.auth.dto.LoginProvider;
import org.platanus.platachat.web.auth.dto.SessionMemberDto;
import org.platanus.platachat.web.member.model.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


//@WebMvcTest(controllers = ChatRestControllerV1.class)
//@MockBeans({
//		@MockBean(JpaMetamodelMappingContext.class) // JpaAuditing
//})
public class ChatRestControllerTest {
	
	@Autowired
	private WebApplicationContext context;
	
	@Test
	void 채팅로그_조회_테스트() throws Exception {
		Member m = Member.builder()
				.username("testUser")
				.nickname("nickname")
				.email("email@gmail.com")
				.provider(LoginProvider.WEB)
				.build();
		MockHttpSession session = new MockHttpSession();
		session.setAttribute("member", new SessionMemberDto(m, "TEST_SESSION_ID"));
		MockMvc mvc = MockMvcBuilders.webAppContextSetup(context).build();
		
		mvc.perform(get("/api/v1/chat/log/simple/test")
				.session(session))
				.andExpect(status().isOk());
		
	}
}
