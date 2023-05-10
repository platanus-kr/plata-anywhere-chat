package org.platanus.platachat.web.auth.session;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import com.fasterxml.jackson.databind.jsontype.PolymorphicTypeValidator;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.platanus.platachat.web.auth.dto.SessionMemberDto;
import org.platanus.platachat.web.auth.serialize.*;
import org.platanus.platachat.web.member.model.Member;
import org.platanus.platachat.web.member.serializer.MemberDeserializer;
import org.platanus.platachat.web.member.serializer.MemberSerializer;
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
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.session.config.annotation.web.http.EnableSpringHttpSession;
import org.springframework.session.data.redis.RedisSessionRepository;

//@Configuration
//@EnableSpringHttpSession
public class SessionClusterSpringHttpSession {

//    @Bean
//    public static ConfigureRedisAction configureRedisAction() {
//        return ConfigureRedisAction.NO_OP;
//    }
    
//    @Bean
    public LettuceConnectionFactory connectionFactory() {
        return new LettuceConnectionFactory();
    }

//    @Bean
    public GenericJackson2JsonRedisSerializer customRedisSerializer() {
        PolymorphicTypeValidator typeValidator = BasicPolymorphicTypeValidator.builder()
                .allowIfBaseType(Object.class)
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
//        module.addSerializer(SessionMemberDto.class, new SessionMemberDtoSerializer());
//        module.addDeserializer(SessionMemberDto.class, new SessionMemberDtoDeserializer());
//        module.addSerializer(Member.class, new MemberSerializer());
//        module.addDeserializer(Member.class, new MemberDeserializer());
//        module.addSerializer(SecurityContextImpl.class, new SecurityContextImplSerializer());
//        module.addDeserializer(SecurityContextImpl.class, new SecurityContextImplDeserializer());
//        module.addSerializer(Authentication.class, new AuthenticationSerializer());
//        module.addDeserializer(Authentication.class, new AuthenticationDeserializer());

        objectMapper.registerModule(module);
        objectMapper.activateDefaultTyping(typeValidator, ObjectMapper.DefaultTyping.JAVA_LANG_OBJECT);
        return new GenericJackson2JsonRedisSerializer(objectMapper);
    }

//    @Bean
    public RedisOperations<String, Object> sessionRedisOperations(LettuceConnectionFactory connectionFactory,
                                                                  GenericJackson2JsonRedisSerializer customRedisSerializer) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
//        redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
        redisTemplate.setValueSerializer(customRedisSerializer);
        redisTemplate.setHashKeySerializer(customRedisSerializer);
//        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(customRedisSerializer);
//        redisTemplate.setHashValueSerializer(new JdkSerializationRedisSerializer());
        //redisTemplate.setDefaultSerializer(new JdkSerializationRedisSerializer()); // 이거 별로 쓸모없음
        //redisTemplate.setEnableDefaultSerializer(true);
//        redisTemplate.setStringSerializer(new StringRedisSerializer());
//        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

//    @Bean
    public RedisSessionRepository sessionRepository(RedisOperations<String, Object> sessionRedisOperations) {
        RedisSessionRepository sessionRepository = new RedisSessionRepository(sessionRedisOperations);
        return sessionRepository;
    }

    
    ///
    
    
//    @Bean(name = {"defaultRedisSerializer", "springSessionDefaultRedisSerializer"})
//    RedisSerializer<Object> defaultRedisSerializer() {
//        Jackson2JsonRedisSerializer result = new Jackson2JsonRedisSerializer(SecurityContextImpl.class);
//        result.setObjectMapper(objectMapper);
//        return result;
//    }
}
