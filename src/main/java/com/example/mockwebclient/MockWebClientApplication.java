package com.example.mockwebclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class MockWebClientApplication {

	@Bean
	WebClient webClient() {
		return WebClient.builder().defaultHeader("Accept", "application/json")
				.defaultHeader("Contents", "application/json").build();
	}

	public static void main(String[] args) {
		SpringApplication.run(MockWebClientApplication.class, args);
	}

}
