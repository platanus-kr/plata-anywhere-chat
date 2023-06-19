package org.platanus.platachat.web.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * JpaRepository 의 위치를 basePackages 로 지정하면 안되고 실제 @Repository 로 등록하는 클래스의 위치를 지정해야함.
 */
//@Configuration
//@EnableJpaRepositories(basePackages = {
//		"org.platanus.platachat.web.member.repository",
//		"org.platanus.platachat.web.room.repository"})
public class CustomSpringDataJpaConfig {
}
