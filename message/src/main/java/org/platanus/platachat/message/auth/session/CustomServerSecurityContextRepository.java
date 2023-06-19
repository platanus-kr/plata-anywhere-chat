package org.platanus.platachat.message.auth.session;

//@Component
//@RequiredArgsConstructor
@Deprecated
public class CustomServerSecurityContextRepository {
//public class CustomServerSecurityContextRepository implements ServerSecurityContextRepository {
	
	private static final String SESSION_KEY_PREFIX = "spring:session:sessions:";
	private static final String SESSION_ATTR_KEY_PREFIX = "sessionAttr:";

//    private final ReactiveRedisOperations<String, String> redisOperations;
//    private final SecurityProperties securityProperties;

//    @Override
//    public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
//        return null;
//    }
//
//    @Override
//    public Mono<SecurityContext> load(ServerWebExchange exchange) {
////        MultiValueMap<String, HttpCookie> cookies = exchange.getRequest().getCookies();
////        if (cookies != null && cookies.containsKey(securityProperties.getSession().getCookie().getName())) {
////            String sessionId = cookies.getFirst(securityProperties.getSession().getCookie().getName()).getValue();
////            String redisKey = SESSION_KEY_PREFIX + sessionId;
////
////            return redisOperations.opsForHash()
////                    .get(redisKey, SESSION_ATTR_KEY_PREFIX + SPRING_SECURITY_CONTEXT_KEY)
////                    .map(value -> {
////                        byte[] contextBytes = Base64.getDecoder().decode((String) value);
////                        Object contextObject = new ObjectInputStream(new ByteArrayInputStream(contextBytes)).readObject();
////                        return new SecurityContextImpl((Authentication) contextObject);
////                    })
////                    .onErrorResume(throwable -> Mono.empty());
////        }
//        return Mono.empty();
//    }
}
