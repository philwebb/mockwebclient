package com.example.mockwebclient.support;

import java.net.URI;
import java.util.Map;
import java.util.function.Supplier;

import org.springframework.util.function.SingletonSupplier;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriBuilderFactory;

import okhttp3.mockwebserver.MockWebServer;

public class MockWebServerUriBuilderFactory implements UriBuilderFactory {

	private final Supplier<MockWebServer> mockWebServer;

	public MockWebServerUriBuilderFactory(MockWebServer mockWebServer) {
		this.mockWebServer = SingletonSupplier.of(mockWebServer);
	}

	public MockWebServerUriBuilderFactory(Supplier<MockWebServer> mockWebServer) {
		this.mockWebServer = mockWebServer;
	}

	@Override
	public URI expand(String uriTemplate, Map<String, ?> uriVariables) {
		return delegate().expand(uriTemplate, uriVariables);
	}

	@Override
	public URI expand(String uriTemplate, Object... uriVariables) {
		return delegate().expand(uriTemplate, uriVariables);
	}

	@Override
	public UriBuilder uriString(String uriTemplate) {
		return delegate().uriString(uriTemplate);
	}

	@Override
	public UriBuilder builder() {
		return delegate().builder();
	}

	private UriBuilderFactory delegate() {
		return new DefaultUriBuilderFactory(this.mockWebServer.get().url("").toString());
	}

}
