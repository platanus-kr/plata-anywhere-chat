package org.platanus.platachat.web.auth.session.serialize;

import java.io.IOException;

import org.springframework.security.core.Authentication;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Deprecated
public class AuthenticationDeserializer extends JsonDeserializer<Authentication> {
	
	private final ObjectMapper objectMapper = new ObjectMapper();
	
	@Override
	public Authentication deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
		try {
			System.out.println("AuthenticationDeserializer 지나가기");
			JsonNode node = jsonParser.getCodec().readTree(jsonParser);
			JsonNode authNode = node.get("authentication");
			
			Authentication authentication = objectMapper.treeToValue(authNode, Authentication.class);
			return authentication;
		} catch (Exception e) {
			throw new IOException("Failed to deserialize Authentication", e);
		}
	}
}
