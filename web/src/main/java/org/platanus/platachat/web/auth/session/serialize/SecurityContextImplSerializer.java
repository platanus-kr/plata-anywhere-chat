package org.platanus.platachat.web.auth.session.serialize;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.security.core.context.SecurityContextImpl;

import java.io.IOException;

@Deprecated
public class SecurityContextImplSerializer extends JsonSerializer<SecurityContextImpl> {

    @Override
    public void serialize(SecurityContextImpl securityContext, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        System.out.println("SecurityContextImplSerializer 지나가기");
        jsonGenerator.writeStartObject();
        jsonGenerator.writeObjectField("authentication", securityContext.getAuthentication());
        jsonGenerator.writeEndObject();
    }
}

