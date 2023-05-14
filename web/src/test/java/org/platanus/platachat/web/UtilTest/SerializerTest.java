package org.platanus.platachat.web.UtilTest;


import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.platanus.platachat.web.auth.session.serialize.SecurityContextImplDeserializer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextImpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

public class SerializerTest {
	
	public static final String SERIALIZED_SECURITY_CONTEXT = "{\"authentication\":{\"authorities\":[{\"authority\":\"ROLE_USER\"}],\"details\":{\"remoteAddress\":\"0:0:0:0:0:0:0:1\",\"sessionId\":null},\"authenticated\":true,\"principal\":{\"id\":1,\"providerId\":null,\"provider\":\"web\",\"username\":\"test1\",\"password\":\"$2a$10$zRdDBj6g.y41E/UYfphLMO2Sd.pqPdgSIHHm7PPdMpxYjxTCWxUc.\",\"nickname\":\"ed858cec8aa4ed8ab81\",\"profileImage\":null,\"htmlUrl\":null,\"email\":\"test1@test.com\",\"deleted\":false,\"appRole\":\"ROLE_USER\",\"lastActivated\":\"2023-05-11T14:17:40.813709\"},\"credentials\":null,\"name\":\"Member(super=org.platanus.platachat.web.member.model.Member@287592a3, id=1, providerId=null, provider=web, username=test1, password=$2a$10$zRdDBj6g.y41E/UYfphLMO2Sd.pqPdgSIHHm7PPdMpxYjxTCWxUc., nickname=ed858cec8aa4ed8ab81, profileImage=null, htmlUrl=null, email=test1@test.com, deleted=false, appRole=ROLE_USER, lastActivated=2023-05-11T14:17:40.813709)\"}}";
	
	@Test
	public void testDeserialize() throws IOException {
		// Setup
		ObjectMapper objectMapper = new ObjectMapper();
		SimpleModule module = new SimpleModule();
		module.addDeserializer(SecurityContextImpl.class, new SecurityContextImplDeserializer());
		objectMapper.registerModule(module);
		
		// When
		//String json = "{\"authentication\": {\"principal\": \"test\", \"authorities\": [\"ROLE_USER\"]}}";
		SecurityContextImpl result = objectMapper.readValue(SERIALIZED_SECURITY_CONTEXT, SecurityContextImpl.class);
		
		// Then
		Authentication authentication = result.getAuthentication();
		System.out.println(authentication.toString());
		//System.out.println(authentication.getPrincipal().toString() + " // " + authentication.getAuthorities().toString() + " // " + authentication.getCredentials().toString());
		// Validate the result using assertions
	}
}
