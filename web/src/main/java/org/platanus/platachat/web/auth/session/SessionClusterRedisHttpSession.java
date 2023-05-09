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
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.session.data.redis.RedisIndexedSessionRepository;
import org.springframework.session.data.redis.RedisSessionRepository;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@Configuration
@EnableRedisHttpSession
public class SessionClusterRedisHttpSession {


    @Bean
    public LettuceConnectionFactory connectionFactory() {
        return new LettuceConnectionFactory();
    }


//    @Bean
//    public GenericJackson2JsonRedisSerializer memberRedisSerializer() {
//        ObjectMapper objectMapper = new ObjectMapper();
//        SimpleModule module = new SimpleModule();
//        module.addSerializer(Member.class, new MemberSerializer());
//        module.addDeserializer(Member.class, new MemberDeserializer());
//        objectMapper.registerModule(module);
//        return new GenericJackson2JsonRedisSerializer(objectMapper);
//    }
//
//    @Bean
//    public RedisTemplate<Object, Object> memberRedisTemplate(LettuceConnectionFactory connectionFactory,
//                                                             GenericJackson2JsonRedisSerializer memberRedisSerializer) {
//        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
//        redisTemplate.setConnectionFactory(connectionFactory);
//        redisTemplate.setKeySerializer(new StringRedisSerializer());
//        redisTemplate.setValueSerializer(memberRedisSerializer);
//        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
//        redisTemplate.setHashValueSerializer(memberRedisSerializer);
//        redisTemplate.setDefaultSerializer(new StringRedisSerializer());
//        return redisTemplate;
//
//    }
//
//    @Bean
//    public RedisIndexedSessionRepository sessionRepository(RedisTemplate<Object, Object> memberRedisTemplate) {
//        RedisIndexedSessionRepository sessionRepository = new RedisIndexedSessionRepository(memberRedisTemplate);
//        return sessionRepository;
//    }

    @Bean
    public GenericJackson2JsonRedisSerializer sessionMemberDtoRedisSerializer() {
        ObjectMapper objectMapper = new ObjectMapper();
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

    @Bean
    public RedisTemplate<Object, Object> sessionMemberDtoRedisTemplate(LettuceConnectionFactory connectionFactory,
                                                                                 GenericJackson2JsonRedisSerializer sessionMemberDtoRedisSerializer) {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(sessionMemberDtoRedisSerializer);
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(sessionMemberDtoRedisSerializer);
//        redisTemplate.setDefaultSerializer(new StringRedisSerializer());
//        redisTemplate.setEnableDefaultSerializer(true);
//        redisTemplate.setStringSerializer(new StringRedisSerializer());
//        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    @Bean
    public RedisIndexedSessionRepository sessionRepository(RedisTemplate<Object, Object> sessionMemberDtoRedisTemplate) {
        RedisIndexedSessionRepository sessionMemberRepository = new RedisIndexedSessionRepository(sessionMemberDtoRedisTemplate);
//        sessionMemberRepository.setDefaultSerializer(new SpringSessionRedisSerializer());
        return sessionMemberRepository;
    }

//
//    @Bean
//    public GenericJackson2JsonRedisSerializer sessionMemberDtoRedisSerializer() {
//        ObjectMapper objectMapper = new ObjectMapper();
//        SimpleModule module = new SimpleModule();
//        module.addSerializer(SessionMemberDto.class, new SessionMemberDtoSerializer());
//        module.addDeserializer(SessionMemberDto.class, new SessionMemberDtoDeserializer());
//        objectMapper.registerModule(module);
//        return new GenericJackson2JsonRedisSerializer(objectMapper);
//    }

//    @Bean
//    public RedisTemplate<String, SessionMemberDto> sessionMemberDtoRedisTemplate(RedisConnectionFactory redisConnectionFactory,
//                                                                                 GenericJackson2JsonRedisSerializer sessionMemberDtoRedisSerializer) {
//        RedisTemplate<String, SessionMemberDto> template = new RedisTemplate<>();
//        template.setConnectionFactory(redisConnectionFactory);
//        template.setKeySerializer(new StringRedisSerializer());
//        template.setValueSerializer(sessionMemberDtoRedisSerializer);
//        template.setHashKeySerializer(new StringRedisSerializer());
//        template.setHashValueSerializer(sessionMemberDtoRedisSerializer);
//        return template;
//    }


    //private final RedisConnectionFactory redisConnectionFactory;

    //@Bean
    //public RedisTemplate<Object, Object> redisTemplate() {
    //    RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
    //    redisTemplate.setConnectionFactory(connectionFactory());
    //    redisTemplate.setKeySerializer(new StringRedisSerializer());
    //    redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
    //    return redisTemplate;
    //}
    //
    //@Bean
    //public RedisIndexedSessionRepository sessionRepository() {
    //    //sessionRepository.setDefaultMaxInactiveInterval(1800);
    //    return new RedisIndexedSessionRepository(redisTemplate());
    //}
    //
    ////@Bean
    ////public RedisHttpSessionConfiguration redisHttpSessionConfiguration() {
    ////    RedisHttpSessionConfiguration redisHttpSessionConfiguration = new RedisHttpSessionConfiguration();
    ////    //redisHttpSessionConfiguration.setMaxInactiveIntervalInSeconds(1800);
    ////    return redisHttpSessionConfiguration;
    ////}
    ////
    //


    /**
     * 이거 작동함
     * @param connectionFactory
     * @return
     */
    //@Bean
    //public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
    //    GenericJackson2JsonRedisSerializer serializer = new GenericJackson2JsonRedisSerializer(new ObjectMapper());
    //    RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
    //    redisTemplate.setKeySerializer(new StringRedisSerializer());
    //    redisTemplate.setValueSerializer(serializer);
    //    redisTemplate.setHashKeySerializer(new StringRedisSerializer());
    //    redisTemplate.setHashValueSerializer(serializer);
    //    redisTemplate.setConnectionFactory(connectionFactory);
    //    return redisTemplate;
    //
    //}


}
