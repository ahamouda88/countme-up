package com.countme.up.spring.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * This Spring Configuration class, is used for launching the application
 * 
 * @author ahamouda
 * 
 */
@SpringBootApplication(scanBasePackages = { "com.countme.up.dao", "com.countme.up.service" })
@EntityScan(basePackages = "com.countme.up.model.entity")
public class ApplicationConfig extends SpringBootServletInitializer {

	public static void main(String... args) {
		SpringApplication.run(ApplicationConfig.class);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(ApplicationConfig.class);
	}

}
