package com.example.mockwebclient.support;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration(proxyBeanMethods = false)
class EnableMockWebServerConfig {

	@Bean
	MockWebServerBean mockWebServerBean() {
		return new MockWebServerBean();
	}

	@Bean
	static WebClientMockWebServerBeanPostProcessor webClientMockWebServerBeanPostProcessor(
			MockWebServerBean mockWebServerBean) {
		return new WebClientMockWebServerBeanPostProcessor("webClient", mockWebServerBean);
	}



}
