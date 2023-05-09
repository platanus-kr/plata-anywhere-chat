package org.platanus.platachat.web.member.serializer;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import org.platanus.platachat.web.member.model.AppRole;
import org.platanus.platachat.web.member.model.Member;

import java.io.IOException;
import java.time.LocalDateTime;

public class MemberDeserializer extends JsonDeserializer<Member> {
    @Override
    public Member deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);

        long id = node.get("id").longValue();
        String providerId = node.get("providerId").asText();
        String provider = node.get("provider").asText();
        String username = node.get("username").asText();
        String password = node.get("password").asText();
        String nickname = node.get("nickname").asText();
        String profileImage = node.get("profileImage").asText();
        String htmlUrl = node.get("htmlUrl").asText();
        String email = node.get("email").asText();
        Boolean deleted = node.get("deleted").asBoolean();
        AppRole appRole = AppRole.valueOf(node.get("appRole").asText());
        LocalDateTime lastActivated = LocalDateTime.parse(node.get("lastActivated").asText());

        return Member.builder()
                .id(id)
                .providerId(providerId)
                .provider(provider)
                .username(username)
                .password(password)
                .nickname(nickname)
                .profileImage(profileImage)
                .htmlUrl(htmlUrl)
                .email(email)
                .deleted(deleted)
                .appRole(appRole)
                .lastActivated(lastActivated)
                .build();
    }
}
