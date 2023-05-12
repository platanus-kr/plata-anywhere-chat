package org.platanus.platachat.message.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

@Slf4j
@Deprecated
//@Component
public class SessionHandlerForMVC {
    public Mono<Void> handleRequest(ServerWebExchange exchange) {
        // 세션 정보 조회
//        Mono<WebSession> sessionMono = exchange.getSession();
//        Flux<DataBuffer> body = exchange.getRequest().getBody();
//        exchange.getRequest().getBody().doOnNext(dataBuffer -> {
//            log.info("dataBuffer: {}", dataBuffer.toString());
//        }).then();
//        return Mono.empty();
//        return DataBufferUtils.join(exchange.getRequest().getBody())
//                        .doOnNext(dataBuffer -> {
//                            log.info("dataBuffer: {}", dataBuffer.toString());
//                        }).then();
//        // 세션 정보를 사용하여 처리
//        return sessionMono.doOnNext(session -> {
//            String sessionId = session.getId();
//            System.out.println("Session ID: " + sessionId);
//        }).then();


//        Flux<String> test = Flux.generate(sink -> {
//            sink.next("Hello");
//            sink.complete();
//        });
//        test.flatMap(s -> {
//            log.info("s: {}", s);
//            return Mono.empty();
//        }).then().subscribe();
//        ServerHttpRequest request = exchange.getRequest();
//        Flux<DataBuffer> body = request.getBody();
//        body.flatMap(d -> {
//            ByteBuffer byteBuffer = d.asByteBuffer();
//            Charset charset = StandardCharsets.UTF_8;
//            String data = charset.decode(byteBuffer).toString();
//            log.info("data: {}", data);
//            return Mono.empty();
//        }).subscribe();
        Object cachedRequestBodyObject = exchange.getAttribute("cachedRequestBodyObject");
        log.info("cachedRequestBodyObject: {}", cachedRequestBodyObject);
        return Mono.empty();
    }
}
