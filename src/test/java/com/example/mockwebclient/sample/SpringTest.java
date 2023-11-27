package com.example.mockwebclient.sample;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.mockwebclient.support.EnableMockWebServer;
import com.example.mockwebclient.support.MockWebServerBean;

import okhttp3.mockwebserver.MockResponse;

@SpringBootTest
@EnableMockWebServer
public class SpringTest {

	@Test
	void myTest(@Autowired MockWebServerBean mockWebServerBean, @Autowired WebClient webClient) {
		mockWebServerBean.getMockWebServer().enqueue(new MockResponse()
				.addHeader("Content-Type", "application/json; charset=utf-8").setBody("{\"ip\": \"127.0.0.1\"}"));
		assertThat(webClient.get().retrieve().bodyToMono(String.class).block()).contains("127.0.0.1");
	}

}
