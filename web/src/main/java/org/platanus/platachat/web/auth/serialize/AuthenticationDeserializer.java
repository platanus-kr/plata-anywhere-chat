package org.platanus.platachat.web.auth.serialize;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import org.platanus.platachat.web.auth.dto.SessionMemberDto;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class AuthenticationDeserializer extends JsonDeserializer<Authentication> {

    @Override
    public Authentication deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);

        Collection<GrantedAuthority> authorities = new ArrayList<>();
        for (JsonNode authorityNode : node.get("authorities")) {
            authorities.add(new SimpleGrantedAuthority(authorityNode.asText()));
        }

        Object principal = jsonParser.getCodec().treeToValue(node.get("principal"), SessionMemberDto.class);
        boolean authenticated = node.get("authenticated").asBoolean();
        String name = node.get("name").asText();

        Authentication authentication = new UsernamePasswordAuthenticationToken(principal, null, authorities);
        authentication.setAuthenticated(authenticated);

        return authentication;
    }
}
