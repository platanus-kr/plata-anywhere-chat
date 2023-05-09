package org.platanus.platachat.web.auth.serialize;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import org.platanus.platachat.web.auth.dto.SessionMemberDto;
import org.platanus.platachat.web.member.model.AppRole;

import java.io.IOException;

public class SessionMemberDtoDeserializer extends JsonDeserializer<SessionMemberDto> {

    @Override
    public SessionMemberDto deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);

        long id = node.get("id").longValue();
        String provider = node.get("provider").asText();
        String username = node.get("username").asText();
        String profileImage = node.get("profileImage").asText();
        String htmlUrl = node.get("htmlUrl").asText();
        String nickname = node.get("nickname").asText();
        String email = node.get("email").asText();
        String appRole = node.get("appRole").asText();
        String token = node.get("token").asText();

        return SessionMemberDto.builder()
                .id(id)
                .provider(provider)
                .username(username)
                .profileImage(profileImage)
                .htmlUrl(htmlUrl)
                .nickname(nickname)
                .email(email)
                .appRole(AppRole.valueOf(appRole))
                .token(token)
                .build();
    }
}