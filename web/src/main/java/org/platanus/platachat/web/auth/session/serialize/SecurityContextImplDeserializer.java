package org.platanus.platachat.web.auth.session.serialize;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

@Deprecated
public class SecurityContextImplDeserializer extends JsonDeserializer<SecurityContextImpl> {
    @Override
    public SecurityContextImpl deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        try {
            System.out.println("SecurityContextImplDeserializer 지나가기");
            JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        
            // Create Authentication manually
            String principal = node.get("authentication").get("principal").asText();
            Collection<GrantedAuthority> authorities = new ArrayList<>();
            for (JsonNode authNode : node.get("authentication").get("authorities")) {
                String authority = authNode.get("authority").asText();
                authorities.add(new SimpleGrantedAuthority(authority));
            }
            Authentication authentication = new UsernamePasswordAuthenticationToken(principal, null, authorities);
        
            return new SecurityContextImpl(authentication);
        } catch (Exception e) {
            throw new IOException("Failed to deserialize SecurityContextImpl", e);
        }
    }
    
}

