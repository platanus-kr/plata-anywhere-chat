package org.platanus.platachat.web.auth.serialize;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextImpl;

import java.io.IOException;

public class SecurityContextImplDeserializer extends JsonDeserializer<SecurityContextImpl> {
    
    @Override
    public SecurityContextImpl deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        //JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        //Authentication authentication = jsonParser.getCodec().treeToValue(node.get("authentication"), Authentication.class);
        //return new SecurityContextImpl(authentication);
        try {
            JsonNode node = jsonParser.getCodec().readTree(jsonParser);
            JsonParser authParser = node.get("authentication").traverse(jsonParser.getCodec());
            Authentication authentication = deserializationContext.readValue(authParser, Authentication.class);
            return new SecurityContextImpl(authentication);
        } catch (Exception e) {
            throw new IOException("Failed to deserialize SecurityContextImpl", e);
        }
    }
}

