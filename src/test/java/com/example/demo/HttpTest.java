package com.example.demo;


import static org.assertj.core.api.Assertions.assertThat;
import org.assertj.core.api.AbstractStringAssert;
import org.assertj.core.api.ObjectAssert;

import org.junit.jupiter.api.Test;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import com.example.demo.controller.AccountController;
import com.example.demo.controller.CustomerController;
import com.example.demo.controller.TransactionController;


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class HttpRequestTest {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	private String getUrl(String uri) {
		return "http://localhost:" + port + uri;
	}

	private String getQuery(String path) {
		return this.restTemplate.getForObject(
			getUrl(path),
			String.class
		);
	}

	private ObjectAssert<JSONObject> assertGetQueryObject(String path) throws JSONException {
		return assertThat(
				new JSONObject(
					getQuery(path)
				)
		);
	}

	private ObjectAssert<JSONArray> assertGetQueryArray(String path) throws JSONException{
		return assertThat(
				new JSONArray(
					getQuery(path)
				)
		);
	}

	private String postQuery(String path, JSONObject data) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<String>(data.toString(), headers);
		return this.restTemplate.postForObject(
			getUrl(path),
			request,
			String.class
		);
	}

	private ObjectAssert<JSONObject> assertPostQueryObject(String path, JSONObject data) throws JSONException {
		return assertThat(
				new JSONObject(
					postQuery(path, data)
				)
		);
	}


	@Test
	void customersEmpty() throws Exception {
		JSONArray expected = new JSONArray("[]");
		assertGetQueryArray("/api/customers").isEqualTo(expected);
	}
}
