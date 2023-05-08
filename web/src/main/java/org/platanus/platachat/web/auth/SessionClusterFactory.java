package org.platanus.platachat.web.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.session.data.redis.RedisIndexedSessionRepository;
import org.springframework.session.data.redis.RedisOperationsSessionRepository;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.data.redis.config.annotation.web.http.RedisHttpSessionConfiguration;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableRedisHttpSession
//@RequiredArgsConstructor
public class SessionClusterFactory {
    
    //private final RedisConnectionFactory redisConnectionFactory;
    
    @Bean
    public LettuceConnectionFactory connectionFactory() {
        return new LettuceConnectionFactory();
    }
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
