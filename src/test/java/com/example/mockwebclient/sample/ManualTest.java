package com.example.mockwebclient.sample;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

public class ManualTest {

	private MockWebServer mockWebServer = new MockWebServer();

	@BeforeEach
	void setup() throws IOException {
		this.mockWebServer.start();
	}

	@AfterEach
	void teardown() throws IOException {
		this.mockWebServer.close();
	}

	@Test
	void myTest() {
		this.mockWebServer.enqueue(new MockResponse().addHeader("Content-Type", "application/json; charset=utf-8")
				.setBody("{\"ip\": \"127.0.0.1\"}"));
		WebClient webClient = WebClient.builder().baseUrl(this.mockWebServer.url("").toString()).build();
		assertThat(webClient.get().retrieve().bodyToMono(String.class).block()).contains("127.0.0.1");
	}

}
