package org.platanus.platachat.web.auth.session;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.platanus.platachat.web.auth.dto.SessionMemberDto;
import org.platanus.platachat.web.auth.serialize.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.session.config.annotation.web.http.EnableSpringHttpSession;
import org.springframework.session.data.redis.RedisSessionRepository;

//@Configuration
//@EnableSpringHttpSession
public class SessionClusterSpringHttpSession {

//    @Bean
//    public static ConfigureRedisAction configureRedisAction() {
//        return ConfigureRedisAction.NO_OP;
//    }

//    private ObjectMapper objectMapper;

//    @Bean
    public LettuceConnectionFactory connectionFactory() {
        return new LettuceConnectionFactory();
    }

//    @Bean
    public GenericJackson2JsonRedisSerializer sessionMemberDtoRedisSerializer() {
        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addSerializer(SessionMemberDto.class, new SessionMemberDtoSerializer());
        module.addDeserializer(SessionMemberDto.class, new SessionMemberDtoDeserializer());
        module.addSerializer(SecurityContextImpl.class, new SecurityContextImplSerializer());
        module.addDeserializer(SecurityContextImpl.class, new SecurityContextImplDeserializer());
        module.addSerializer(Authentication.class, new AuthenticationSerializer());
        module.addDeserializer(Authentication.class, new AuthenticationDeserializer());
        objectMapper.registerModule(module);
        return new GenericJackson2JsonRedisSerializer(objectMapper);
    }

//    @Bean
    public RedisOperations<String, Object> sessionRedisOperations(LettuceConnectionFactory connectionFactory,
                                                                  GenericJackson2JsonRedisSerializer sessionMemberDtoRedisSerializer) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(sessionMemberDtoRedisSerializer);
        template.setHashKeySerializer(new GenericJackson2JsonRedisSerializer());
        template.setHashValueSerializer(sessionMemberDtoRedisSerializer);
//        template.setDefaultSerializer(new StringRedisSerializer());
        return template;
    }

//    @Bean
    public RedisSessionRepository sessionRepository(RedisOperations<String, Object> sessionRedisOperations) {
        RedisSessionRepository sessionRepository = new RedisSessionRepository(sessionRedisOperations);
        return sessionRepository;
    }

//    @Bean(name = {"defaultRedisSerializer", "springSessionDefaultRedisSerializer"})
//    RedisSerializer<Object> defaultRedisSerializer() {
//        Jackson2JsonRedisSerializer result = new Jackson2JsonRedisSerializer(SecurityContextImpl.class);
//        result.setObjectMapper(objectMapper);
//        return result;
//    }
}
