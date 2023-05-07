package org.platanus.platachat.message.auth;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.session.data.redis.config.annotation.web.server.EnableRedisWebSession;

@Configuration
@EnableRedisWebSession
public class SessionClusterFactory {

    @Bean
    public LettuceConnectionFactory connectionFactory() {
        return new LettuceConnectionFactory();
    }
//    @Value("${spring.redis.host}")
//    private String redisHost;
//
//    @Value("${spring.redis.port}")
//    private int redisPort;
//
//    @Bean
//    public ReactiveRedisConnectionFactory reactiveRedisConnectionFactory() {
//        return new LettuceConnectionFactory(redisHost, redisPort);
////        return new LettuceConnectionFactory();
//    }


//    @Bean
//    public ReactiveSessionRepository<? extends Session> reactiveRedisSessionRepository(ReactiveRedisConnectionFactory reactiveRedisConnectionFactory) {
//        return new ReactiveRedisSessionRepository((ReactiveRedisOperations<String, Object>) reactiveRedisConnectionFactory);
//    }


}
