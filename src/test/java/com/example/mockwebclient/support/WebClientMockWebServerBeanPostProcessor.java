package com.example.mockwebclient.support;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.web.reactive.function.client.WebClient;

public class WebClientMockWebServerBeanPostProcessor implements BeanFactoryPostProcessor {

	private final String beanName;

	private final MockWebServerBean mockWebServerBean;

	public WebClientMockWebServerBeanPostProcessor(String beanName, MockWebServerBean mockWebServerBean) {
		this.beanName = beanName;
		this.mockWebServerBean = mockWebServerBean;
	}

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		if (beanFactory.containsBeanDefinition(this.beanName)) {
			BeanDefinition beanDefinition = new RootBeanDefinition(WebClient.class, this::buildWebClient);
			BeanDefinitionRegistry beanDefinitionRegistry = (BeanDefinitionRegistry) beanFactory;
			beanDefinitionRegistry.removeBeanDefinition(this.beanName);
			beanDefinitionRegistry.registerBeanDefinition(this.beanName, beanDefinition);
		}
	}

	private WebClient buildWebClient() {
		return WebClient.builder().defaultHeader("Accept", "application/json")
				.defaultHeader("Contents", "application/json")
				.uriBuilderFactory(new MockWebServerUriBuilderFactory(this.mockWebServerBean::getMockWebServer))
				.build();
	}

}
