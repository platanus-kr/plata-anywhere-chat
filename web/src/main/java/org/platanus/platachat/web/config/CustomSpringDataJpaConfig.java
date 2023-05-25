package org.platanus.platachat.web.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = {
		"org.platanus.platachat.web.member.repository.jpa",
		"org.platanus.platachat.web.room.repository.jpa"})
public class CustomSpringDataJpaConfig {
}
