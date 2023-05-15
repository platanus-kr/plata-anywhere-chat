package org.platanus.platachat.message.auth.session;


//@Configuration
//@EnableRedisWebSession
@Deprecated
public class SessionClusterRedisWebSessionConfig {
	
	//@Bean
	//public LettuceConnectionFactory connectionFactory() {
	//    return new LettuceConnectionFactory();
	//}
	
	//@Bean
	//public GenericJackson2JsonRedisSerializer sessionMemberDtoRedisSerializer() {
	//    ObjectMapper objectMapper = new ObjectMapper();
	//    SimpleModule module = new SimpleModule();
	//    //module.addSerializer(SessionMemberDto.class, new SessionMemberDtoSerializer());
	//    //module.addDeserializer(SessionMemberDto.class, new SessionMemberDtoDeserializer());
	//    objectMapper.registerModule(module);
	//    return new GenericJackson2JsonRedisSerializer(objectMapper);
	//}
	
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
	//public ReactiveRedisTemplate<String, Object> reactiveRedisTemplate(ReactiveRedisConnectionFactory factory) {
	//    StringRedisSerializer keySerializer = new StringRedisSerializer();
	//    JdkSerializationRedisSerializer valueSerializer = new JdkSerializationRedisSerializer();
	//
	//    RedisSerializationContext.RedisSerializationContextBuilder<String, Object> builder = RedisSerializationContext.newSerializationContext(keySerializer);
	//    RedisSerializationContext<String, Object> context = builder.value(valueSerializer).hashKey(keySerializer).hashValue(valueSerializer).build();
	//
	//    return new ReactiveRedisTemplate<>(factory, context);
	//}
	
}