package org.platanus.platachat.web.member.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.platanus.platachat.web.member.model.Member;

import java.io.IOException;

public class MemberSerializer extends JsonSerializer<Member> {


    @Override
    public void serialize(Member member, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("id", member.getId());
        jsonGenerator.writeStringField("providerId", member.getProviderId());
        jsonGenerator.writeStringField("provider", member.getProvider());
        jsonGenerator.writeStringField("username", member.getUsername());
        jsonGenerator.writeStringField("password", member.getPassword());
        jsonGenerator.writeStringField("nickname", member.getNickname());
        jsonGenerator.writeStringField("profileImage", member.getProfileImage());
        jsonGenerator.writeStringField("htmlUrl", member.getHtmlUrl());
        jsonGenerator.writeStringField("email", member.getEmail());
        jsonGenerator.writeBooleanField("deleted", member.getDeleted());
        jsonGenerator.writeStringField("appRole", member.getAppRole().toString());
        jsonGenerator.writeStringField("lastActivated", member.getLastActivated().toString());
        jsonGenerator.writeEndObject();
    }
}
