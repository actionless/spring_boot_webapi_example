package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;
import org.assertj.core.api.AbstractStringAssert;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.web.server.LocalServerPort;

import com.example.demo.controller.AccountController;
import com.example.demo.controller.CustomerController;
import com.example.demo.controller.TransactionController;


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class HttpRequestTest {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	private AbstractStringAssert getQuery(String path) {
		return assertThat(
				this.restTemplate.getForObject(
					"http://localhost:" + port + path,
					String.class
				)
		);
	}

	@Test
	void greetingShouldReturnDefaultMessage() throws Exception {
		getQuery("/api/customers").contains("[]");
	}
}
