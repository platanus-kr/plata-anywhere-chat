package org.platanus.platachat.message.auth.app;

//@Slf4j
//@Component
@Deprecated
public class CustomAuthenticationSuccessHandler {
//public class CustomAuthenticationSuccessHandler extends WebFilterChainServerAuthenticationSuccessHandler {

//    @Override
//    public Mono<Void> onAuthenticationSuccess(WebFilterExchange webFilterExchange, Authentication authentication) {
//        ServerWebExchange exchange = webFilterExchange.getExchange();
//        ServerHttpResponse response = exchange.getResponse();
////        HttpHeaders headers = response.getHeaders();
////        headers.setLocation(URI.create("/")); // 로그인 성공 후 리다이렉트할 페이지
////        response.setStatusCode(HttpStatus.SEE_OTHER);
//
//        //ServerWebExchangeUtils.setAlreadyRouted(exchange);
//
//
//        // 세션에 SessionMemberDto 객체를 저장합니다.
//        return exchange.getSession()
//                .doOnNext(session -> {
//                    if (authentication.getPrincipal() instanceof Member) {
//                        Member member = (Member) authentication.getPrincipal();
//                        session.getAttributes().put("member", new SessionMemberDto(member, session.getId()));
//                    } else {
//                        log.error("authentication.getPrincipal() is not instance of Member");
//                    }
//                }).then();
//    }

//    @Override
//    public Mono<Void> onAuthenticationSuccess(WebFilterExchange webFilterExchange, Authentication authentication) {
//        ServerWebExchange exchange = webFilterExchange.getExchange();
//        HttpSession session = exchange.getSession().block();
//
//        Object principal = authentication.getPrincipal();
//        if (principal instanceof Member) {
//            Member m = (Member) principal;
//            // SessionMemberDto 객체 생성 및 저장
//            session.setAttribute("member", new SessionMemberDto(m, session.getId()));
//        }
//
//        return exchange.getResponse().setComplete();
//    }
}
