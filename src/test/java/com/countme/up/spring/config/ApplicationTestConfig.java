package com.countme.up.spring.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * This Test Spring Configuration class, is used for launching the application, and it is being used for unit and
 * integration testing
 * 
 * @author ahamouda
 * 
 */
@SpringBootApplication(
		scanBasePackages = { "com.countme.up.dao", "com.countme.up.service", "com.countme.up.controller" })
@EntityScan(basePackages = "com.countme.up.model.entity")
public class ApplicationTestConfig extends SpringBootServletInitializer {

	public static void main(String... args) {
		SpringApplication.run(ApplicationConfig.class);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(ApplicationConfig.class);
	}
}
