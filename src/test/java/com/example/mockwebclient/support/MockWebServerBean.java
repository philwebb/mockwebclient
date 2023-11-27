package com.example.mockwebclient.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.test.context.event.AfterTestMethodEvent;
import org.springframework.test.context.event.BeforeTestMethodEvent;
import org.springframework.test.context.event.TestContextEvent;
import org.springframework.util.function.ThrowingConsumer;

import okhttp3.mockwebserver.MockWebServer;

public class MockWebServerBean implements ApplicationListener<TestContextEvent> {

	private static final Logger log = LoggerFactory.getLogger(MockWebServerBean.class);

	private final ThreadLocal<MockWebServer> mockWebServer = new ThreadLocal<>();

	@Override
	public void onApplicationEvent(TestContextEvent event) {
		if (event instanceof BeforeTestMethodEvent) {
			start();
		}
		if (event instanceof AfterTestMethodEvent) {
			close();
		}
	}

	private void start() {
		log.info("starting mock web server");
		call(MockWebServer::close);
		this.mockWebServer.set(new MockWebServer());
		call(MockWebServer::start);
	}

	private void close() {
		log.info("closing mock web server");
		call(MockWebServer::close);
		this.mockWebServer.remove();
	}

	private void call(ThrowingConsumer<MockWebServer> action) {
		MockWebServer mockWebServer = getMockWebServer();
		if (mockWebServer != null) {
			action.accept(mockWebServer);
		}
	}

	public MockWebServer getMockWebServer() {
		return this.mockWebServer.get();
	}

}
