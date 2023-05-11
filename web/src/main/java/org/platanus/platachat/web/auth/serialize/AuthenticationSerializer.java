package org.platanus.platachat.web.auth.serialize;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.io.IOException;

public class AuthenticationSerializer extends JsonSerializer<Authentication> {

    @Override
    public void serialize(Authentication authentication, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        System.out.println("AuthenticationSerializer 시작 지나가기");
        jsonGenerator.writeStartObject();
        jsonGenerator.writeArrayFieldStart("authorities");
        for (GrantedAuthority authority : authentication.getAuthorities()) {
            jsonGenerator.writeString(authority.getAuthority());
        }
        jsonGenerator.writeEndArray();
        jsonGenerator.writeObjectField("principal", authentication.getPrincipal());
        jsonGenerator.writeBooleanField("authenticated", authentication.isAuthenticated());
//        jsonGenerator.writeStringField("name", authentication.getName());
        jsonGenerator.writeEndObject();
        System.out.println("AuthenticationSerializer 끝 지나가기");
    }
}
