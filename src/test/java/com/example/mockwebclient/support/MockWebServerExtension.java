package com.example.mockwebclient.support;

import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ExtensionContext.Namespace;
import org.junit.jupiter.api.extension.ExtensionContext.Store;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import org.springframework.web.reactive.function.client.WebClient;

import okhttp3.mockwebserver.MockWebServer;

public class MockWebServerExtension implements BeforeEachCallback, AfterEachCallback, ParameterResolver {

	private static final Namespace NAMESPACE = Namespace.create(MockWebServerExtension.class);

	@Override
	public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
			throws ParameterResolutionException {
		return extensionContext.getTestMethod().isPresent()
				&& parameterContext.getParameter().getType() == MockWebServer.class
				|| parameterContext.getParameter().getType() == WebClient.Builder.class;
	}

	@Override
	public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
			throws ParameterResolutionException {
		MockWebServer mockWebServer = getMockWebServer(extensionContext);
		if (parameterContext.getParameter().getType() == MockWebServer.class) {
			return mockWebServer;
		}
		if (parameterContext.getParameter().getType() == WebClient.Builder.class) {
			return WebClient.builder().uriBuilderFactory(new MockWebServerUriBuilderFactory(mockWebServer));
		}
		return null;
	}

	@Override
	public void beforeEach(ExtensionContext context) throws Exception {
		getMockWebServer(context).start();
	}

	@Override
	public void afterEach(ExtensionContext context) throws Exception {
		getMockWebServer(context).close();
	}

	private MockWebServer getMockWebServer(ExtensionContext context) {
		Store store = context.getStore(NAMESPACE);
		return store.getOrComputeIfAbsent(context.getUniqueId(), (uniqueId) -> new MockWebServer(),
				MockWebServer.class);
	}

}
