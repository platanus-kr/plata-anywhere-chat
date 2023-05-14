package org.platanus.platachat.web.auth.session.serialize;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import org.platanus.platachat.web.auth.dto.SessionMemberDto;

import java.io.IOException;

@Deprecated
public class SessionMemberDtoSerializer extends JsonSerializer<SessionMemberDto> {

    @Override
    public void serialize(SessionMemberDto sessionMemberDto, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("id", sessionMemberDto.getId());
        jsonGenerator.writeStringField("provider", sessionMemberDto.getProvider());
        jsonGenerator.writeStringField("username", sessionMemberDto.getUsername());
        jsonGenerator.writeStringField("profileImage", sessionMemberDto.getProfileImage());
        jsonGenerator.writeStringField("htmlUrl", sessionMemberDto.getHtmlUrl());
        jsonGenerator.writeStringField("nickname", sessionMemberDto.getNickname());
        jsonGenerator.writeStringField("email", sessionMemberDto.getEmail());
        jsonGenerator.writeStringField("appRole", sessionMemberDto.getAppRole().toString());
        jsonGenerator.writeStringField("token", sessionMemberDto.getToken());
        jsonGenerator.writeEndObject();
    }
}