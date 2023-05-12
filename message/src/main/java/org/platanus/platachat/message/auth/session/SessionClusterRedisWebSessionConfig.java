package org.platanus.platachat.message.auth.session;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.platanus.platachat.message.auth.serialize.SessionMemberDtoDeserializer;
import org.platanus.platachat.message.auth.serialize.SessionMemberDtoSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.session.data.redis.config.annotation.web.server.EnableRedisWebSession;

//@Configuration
//@EnableRedisWebSession
@Deprecated
public class SessionClusterRedisWebSessionConfig {

    //@Bean
    public LettuceConnectionFactory connectionFactory() {
        return new LettuceConnectionFactory();
    }

    //@Bean
    public GenericJackson2JsonRedisSerializer sessionMemberDtoRedisSerializer() {
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        //module.addSerializer(SessionMemberDto.class, new SessionMemberDtoSerializer());
        //module.addDeserializer(SessionMemberDto.class, new SessionMemberDtoDeserializer());
        objectMapper.registerModule(module);
        return new GenericJackson2JsonRedisSerializer(objectMapper);
    }

    //@Bean
    //public ReactiveRedisTemplate<String, Object> reactiveRedisTemplate(ReactiveRedisConnectionFactory connectionFactory) {
    //    //public ReactiveRedisTemplate<String, Object> reactiveRedisTemplate(ReactiveRedisConnectionFactory connectionFactory,
    //    //        GenericJackson2JsonRedisSerializer sessionMemberDtoRedisSerializer) {
    //    StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
    //    JdkSerializationRedisSerializer jdkSerializationRedisSerializer = new JdkSerializationRedisSerializer();
    //    RedisSerializationContext.RedisSerializationContextBuilder<String, Object> builder =
    //            RedisSerializationContext.newSerializationContext(stringRedisSerializer);
    //    //RedisSerializationContext<String, Object> context = builder.value(sessionMemberDtoRedisSerializer).build();
    //    RedisSerializationContext<String, Object> context = builder.value(jdkSerializationRedisSerializer).build();
    //    return new ReactiveRedisTemplate<>(connectionFactory, context);
    //}
    
    //@Bean
    public ReactiveRedisTemplate<String, Object> reactiveRedisTemplate(ReactiveRedisConnectionFactory factory) {
        StringRedisSerializer keySerializer = new StringRedisSerializer();
        JdkSerializationRedisSerializer valueSerializer = new JdkSerializationRedisSerializer();
        
        RedisSerializationContext.RedisSerializationContextBuilder<String, Object> builder = RedisSerializationContext.newSerializationContext(keySerializer);
        RedisSerializationContext<String, Object> context = builder.value(valueSerializer).hashKey(keySerializer).hashValue(valueSerializer).build();
        
        return new ReactiveRedisTemplate<>(factory, context);
    }

}