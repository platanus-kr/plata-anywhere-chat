package org.platanus.platachat.web.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.session.data.redis.RedisOperationsSessionRepository;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableRedisHttpSession
@RequiredArgsConstructor
public class SessionClusterFactory {
    
    
    @Bean
    public LettuceConnectionFactory connectionFactory() {
        return new LettuceConnectionFactory();
    }
    
    
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer()); // JSON 포맷으로 저장
    
        redisTemplate.setConnectionFactory(connectionFactory);
        return redisTemplate;
    }
    
    @Bean
    StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }
    
    //@Bean
    //public RedisSerializer<Object> springSessionDefaultRedisSerializer() {
    //    ObjectMapper mapper = new ObjectMapper();
    //    //mapper.addMixIn(UsernamePasswordAuthenticationToken.class, UsernamePasswordAuthenticationTokenMixin.class);
    //
    //
    //    return new GenericJackson2JsonRedisSerializer(mapper);
    //}
    
    //@Bean
    //public RedisTemplate<String, Object> redisTemplate(){
    //    RedisTemplate<String,Object> redisTemplate = new RedisTemplate<>();
    //    redisTemplate.setConnectionFactory(connectionFactory());
    //    redisTemplate.setDefaultSerializer(new GenericJackson2JsonRedisSerializer());
    //    return redisTemplate;
    //}
    
    
}
