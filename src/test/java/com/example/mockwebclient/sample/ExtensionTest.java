package com.example.mockwebclient.sample;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.mockwebclient.support.MockWebServerExtension;
import com.example.mockwebclient.support.MockWebServerUriBuilderFactory;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

@ExtendWith(MockWebServerExtension.class)
public class ExtensionTest {

	@Test
	void myTestWithJustServer(MockWebServer mockWebServer) {
		mockWebServer.enqueue(new MockResponse().addHeader("Content-Type", "application/json; charset=utf-8")
				.setBody("{\"ip\": \"127.0.0.1\"}"));
		WebClient webClient = WebClient.builder().baseUrl(mockWebServer.url("").toString()).build();
		assertThat(webClient.get().retrieve().bodyToMono(String.class).block()).contains("127.0.0.1");
	}

	@Test
	void myTestWithUriBuilderFactory(MockWebServer mockWebServer) {
		mockWebServer.enqueue(new MockResponse().addHeader("Content-Type", "application/json; charset=utf-8")
				.setBody("{\"ip\": \"127.0.0.1\"}"));
		WebClient webClient = WebClient.builder().uriBuilderFactory(new MockWebServerUriBuilderFactory(mockWebServer)).build();
		assertThat(webClient.get().retrieve().bodyToMono(String.class).block()).contains("127.0.0.1");
	}

	@Test
	void myTestWithWebClientBuilder(MockWebServer mockWebServer, WebClient.Builder webClientBuilder) {
		mockWebServer.enqueue(new MockResponse().addHeader("Content-Type", "application/json; charset=utf-8")
				.setBody("{\"ip\": \"127.0.0.1\"}"));
		WebClient webClient = webClientBuilder.build();
		assertThat(webClient.get().retrieve().bodyToMono(String.class).block()).contains("127.0.0.1");
	}

}
