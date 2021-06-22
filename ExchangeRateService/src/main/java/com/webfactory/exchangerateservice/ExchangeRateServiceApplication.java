package com.webfactory.exchangerateservice;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

import com.webfactory.exchangerateservice.model.Role;
import com.webfactory.exchangerateservice.model.User;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableScheduling
@EnableSwagger2
@EnableAuthorizationServer
@EnableResourceServer
public class ExchangeRateServiceApplication {
	public static volatile CacheManager cacheManager;
	public static List<User> users = new ArrayList<User>();
	public static final String cacheKey = "cacheKey123";
	public static final String cacheName = "exchangeRateCache";
	private static int cacheMaxSize = 10000;
	private static int cacheExpiryHours = 3600;

	static {
		User u = new User();
		u.setEmail("robmoi2010@gmail.com");
		List<Role> roles = new ArrayList<Role>();
		roles.add(new Role() {
			{
				setName("SYSADMIN");
			}
		});
		roles.add(new Role() {
			{
				setName("USER");
			}
		});
		u.setRoles(roles);
		u.setUsername("robmoi");
		u.setPassword(new BCryptPasswordEncoder().encode("password"));
		users.add(u);

		cacheManager = CacheManagerBuilder.newCacheManagerBuilder().withCache(cacheName, CacheConfigurationBuilder
				.newCacheConfigurationBuilder(String.class, Object.class, ResourcePoolsBuilder.heap(cacheMaxSize))
				.withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofHours(cacheExpiryHours)))).build(true);
	}

	public static void main(String[] args) {
		SpringApplication.run(ExchangeRateServiceApplication.class, args);
	}

	@Bean
	public Docket productApi() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.webfactory.exchangerateservice.controller")).build();
	}

}
