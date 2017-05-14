package com.countme.up.spring.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

import com.countme.up.model.entity.Candidate;
import com.countme.up.model.entity.Voter;
import com.countme.up.service.CandidateService;
import com.countme.up.service.VoterService;

/**
 * This Spring Configuration class, is used for launching the application
 * 
 * @author ahamouda
 * 
 */
@SpringBootApplication(
		scanBasePackages = { "com.countme.up.dao", "com.countme.up.service", "com.countme.up.controller" })
@EntityScan(basePackages = "com.countme.up.model.entity")
public class ApplicationConfig extends SpringBootServletInitializer {

	public static void main(String... args) {
		SpringApplication.run(ApplicationConfig.class);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(ApplicationConfig.class);
	}

	/**
	 * A Bean for initializing and setting up the database, by creating 4 candidates, and 20 voters
	 * 
	 * @param voterService
	 *            the {@link VoterService}
	 * @param candidateService
	 *            the {@link CandidateService}
	 * @return a {@link CommandLineRunner}
	 */
	@Bean
	public CommandLineRunner databaseSetup(VoterService voterService, CandidateService candidateService) {
		return (args) -> {
			/** Create voters **/
			for (int i = 1; i <= 20; i++) {
				voterService.add(new Voter("FNVoter_" + i, "LNVoter_" + i, "EmailVoter_" + i));
			}

			/** Create candidates **/
			for (int i = 1; i <= 4; i++) {
				candidateService.add(new Candidate("FNCandidate_" + i, "LNCandidate_" + i, "EmailCandidate_" + i,
						"ProgramCandidate_" + i));
			}
		};
	}
}
