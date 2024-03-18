package com.example.demo;


import static org.assertj.core.api.Assertions.assertThat;
import org.assertj.core.api.AbstractStringAssert;
import org.assertj.core.api.ObjectAssert;

import org.junit.jupiter.api.Test;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import org.skyscreamer.jsonassert.JSONAssert;

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

	private void assertGetQueryArray(String path, JSONObject expected) throws Exception {
		JSONAssert.assertEquals(
			getQuery(path),
			expected,
			false
		);
	}

	private void assertGetQueryArray(String path, JSONArray expected) throws Exception {
		JSONAssert.assertEquals(
			getQuery(path),
			expected,
			false
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

	private void assertPostQueryObject(String path, JSONObject payload, JSONObject expected) throws Exception {
		JSONAssert.assertEquals(
			postQuery(path, payload),
			expected,
			false
		);
	}


	@Test
	void customersEmpty() throws Exception {
		JSONArray expected = new JSONArray("[]");
		assertGetQueryArray("/api/customers", expected);
	}

	@Test
	void customersAddOne() throws Exception {
		JSONObject payload = new JSONObject("{'name':'Alice', 'surname': 'Bright'}");
		JSONObject expected = new JSONObject("{'id':0,'name':'Alice','surname':'Bright'}");
		assertPostQueryObject("/api/customer", payload, expected);
	}

}
