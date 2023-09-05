package org.platanus.platachat.message.environment.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/v1/environment")
@RequiredArgsConstructor
public class EnvironmentController {

    private final Environment environment;

    @CrossOrigin(maxAge = 3600)
    @GetMapping("/profiles")
    public Mono<List<String>> getCurrentProfile() {
        String[] activeProfiles = environment.getActiveProfiles();
        List<String> profiles = Arrays.stream(activeProfiles).toList();
        return Mono.just(profiles);
    }
}
