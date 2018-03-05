package org.myazure.ntxf;

import org.myazure.ntxf.configuration.NtxfEmbeddedServletContainerCustomizer;
import org.myazure.ntxf.controller.MPController;
import org.myazure.ntxf.controller.MenuController;
import org.myazure.ntxf.controller.UserController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import weixin.popular.support.TokenManager;

@SpringBootApplication
@EnableAutoConfiguration
@Configuration
@ServletComponentScan
@ComponentScan
@EnableTransactionManagement
@EnableJpaRepositories
@EnableJpaAuditing
// @EnableCaching
public class NtxfApplication {
	private static final Logger LOG = LoggerFactory
			.getLogger(NtxfApplication.class);

	@Bean
	public EmbeddedServletContainerCustomizer embeddedServletCustomizer() {
		return new NtxfEmbeddedServletContainerCustomizer();
	}

	// @Bean
	// public RestTem plate restTemplate() {
	// return new RestTemplate(clientHttpRequestFactory());
	// }

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	private ClientHttpRequestFactory clientHttpRequestFactory() {
		SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
		factory.setReadTimeout(10000);
		factory.setConnectTimeout(10000);
		return factory;
	}

	public static void main(String[] args) {
		SpringApplication.run(NtxfApplication.class, args);
		LOG.info("System Ready!!");
	}
}
