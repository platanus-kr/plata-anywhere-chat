package org.platanus.platachat.message.auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController("/tetete")
public class AuthTestController {

    @GetMapping("/te")
    public Mono<String> test() {
        return Mono.just("test");
    }
}
