package org.platanus.platachat.web.auth.session;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.platanus.platachat.web.auth.dto.SessionMemberDto;
import org.platanus.platachat.web.auth.session.serialize.*;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.session.data.redis.RedisIndexedSessionRepository;

//@Configuration
//@EnableRedisHttpSession
@Deprecated
public class RedisHttpSessionClusterConfig {


    //@Bean
    public LettuceConnectionFactory connectionFactory() {
        return new LettuceConnectionFactory();
    }

    //@Bean
    public GenericJackson2JsonRedisSerializer customRedisSerializer() {
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addSerializer(SessionMemberDto.class, new SessionMemberDtoSerializer());
        module.addDeserializer(SessionMemberDto.class, new SessionMemberDtoDeserializer());
        //module.addSerializer(Member.class, new MemberSerializer());
        //module.addDeserializer(Member.class, new MemberDeserializer());
        module.addSerializer(SecurityContextImpl.class, new SecurityContextImplSerializer());
        module.addDeserializer(SecurityContextImpl.class, new SecurityContextImplDeserializer());
        module.addSerializer(Authentication.class, new AuthenticationSerializer());
        module.addDeserializer(Authentication.class, new AuthenticationDeserializer());

        //test1
        objectMapper.registerModule(module);
//        objectMapper.registerModule(new JavaTimeModule());

        /**
         *  이부분은 위의 직렬화기/역직렬화기와 양자택일임.
         *  다만 SecurityContextImpl 과 같이 @JsonTypeInfo 명시가 불가능한경우
         *  직렬화를 위처럼 직접 구현해야한다.
         */
        // https://velog.io/@bagt/Redis-%EC%97%AD%EC%A7%81%EB%A0%AC%ED%99%94-%EC%82%BD%EC%A7%88%EA%B8%B0-feat.-RedisSerializer
        //PolymorphicTypeValidator typeValidator = BasicPolymorphicTypeValidator.builder()
        //        .allowIfBaseType(Object.class)
        //        .build();
        //objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
//        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE); // 일단이거
//        objectMapper.activateDefaultTyping(typeValidator, ObjectMapper.DefaultTyping.NON_FINAL); // 너도
        //objectMapper.activateDefaultTyping(typeValidator, ObjectMapper.DefaultTyping.JAVA_LANG_OBJECT);
        return new GenericJackson2JsonRedisSerializer(objectMapper);
    }

    //@Bean
//    public RedisTemplate<String, Object> customSecurityContextRedisTemplate(LettuceConnectionFactory connectionFactory) {
    public RedisTemplate<Object, Object> customSecurityContextRedisTemplate(LettuceConnectionFactory connectionFactory,
                                                                            GenericJackson2JsonRedisSerializer customRedisSerializer) {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
//        redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
        redisTemplate.setValueSerializer(customRedisSerializer);
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
//        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(customRedisSerializer);
//        redisTemplate.setHashValueSerializer(new JdkSerializationRedisSerializer());
        redisTemplate.setDefaultSerializer(customRedisSerializer); // 이거 별로 쓸모없음
        redisTemplate.setEnableDefaultSerializer(true);
//        redisTemplate.setStringSerializer(new StringRedisSerializer());
//        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    //@Bean
    public RedisIndexedSessionRepository sessionRepository(RedisTemplate<Object, Object> customSecurityContextRedisTemplate) {
        RedisIndexedSessionRepository sessionMemberRepository = new RedisIndexedSessionRepository(customSecurityContextRedisTemplate);
        return sessionMemberRepository;
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


