package org.platanus.platachat.web.auth.session;

import org.platanus.platachat.web.auth.session.serialize.SecurityContextImplDeserializer;
import org.platanus.platachat.web.auth.session.serialize.SecurityContextImplSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.session.config.annotation.web.http.EnableSpringHttpSession;
import org.springframework.session.data.redis.RedisSessionRepository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

@Configuration
@EnableSpringHttpSession
public class SpringHttpSessionClusterConfig {
	
	@Bean
	public LettuceConnectionFactory connectionFactory() {
		return new LettuceConnectionFactory();
	}
	
	//@Bean
	@Deprecated
	public GenericJackson2JsonRedisSerializer customRedisSerializer() {
		ObjectMapper objectMapper = new ObjectMapper();
		SimpleModule module = new SimpleModule();
		//module.addSerializer(SessionMemberDto.class, new SessionMemberDtoSerializer());
		//module.addDeserializer(SessionMemberDto.class, new SessionMemberDtoDeserializer());
		//module.addSerializer(Member.class, new MemberSerializer());
		//module.addDeserializer(Member.class, new MemberDeserializer());
		module.addSerializer(SecurityContextImpl.class, new SecurityContextImplSerializer());
		module.addDeserializer(SecurityContextImpl.class, new SecurityContextImplDeserializer());
		//module.addSerializer(Authentication.class, new AuthenticationSerializer());
		//module.addDeserializer(Authentication.class, new AuthenticationDeserializer());
		
		//test1
		objectMapper.registerModule(module);
		//objectMapper.registerModule(new JavaTimeModule());
		
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
		//objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE); // 일단이거
		//objectMapper.activateDefaultTyping(typeValidator, ObjectMapper.DefaultTyping.NON_FINAL); // 너도
		//objectMapper.activateDefaultTyping(typeValidator, ObjectMapper.DefaultTyping.JAVA_LANG_OBJECT);
		return new GenericJackson2JsonRedisSerializer(objectMapper);
	}
	
	//@Bean
	@Deprecated
	public RedisOperations<String, Object> sessionRedisOperationsLegacy(LettuceConnectionFactory connectionFactory, GenericJackson2JsonRedisSerializer customRedisSerializer) {
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(connectionFactory);
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setValueSerializer(customRedisSerializer);
		redisTemplate.setHashKeySerializer(new StringRedisSerializer());
		redisTemplate.setHashValueSerializer(customRedisSerializer);
		redisTemplate.setDefaultSerializer(customRedisSerializer);
		redisTemplate.setEnableDefaultSerializer(true);
		return redisTemplate;
	}
	
	@Bean
	public RedisOperations<String, Object> sessionRedisOperations(LettuceConnectionFactory connectionFactory) {
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(connectionFactory);
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
		redisTemplate.setHashKeySerializer(new StringRedisSerializer());
		redisTemplate.setHashValueSerializer(new JdkSerializationRedisSerializer());
		redisTemplate.setDefaultSerializer(new JdkSerializationRedisSerializer());
		redisTemplate.setEnableDefaultSerializer(true);
		redisTemplate.afterPropertiesSet();
		return redisTemplate;
	}
	
	@Bean
	public RedisSessionRepository sessionRepository(RedisOperations<String, Object> sessionRedisOperations) {
		return new RedisSessionRepository(sessionRedisOperations);
	}
	
	//@Bean
//    public static ConfigureRedisAction configureRedisAction() {
//        return ConfigureRedisAction.NO_OP;
//    }
}
