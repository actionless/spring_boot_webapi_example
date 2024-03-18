package com.example.demo;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.json.JSONArray;
import org.json.JSONObject;

import org.skyscreamer.jsonassert.JSONAssert;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;

import com.example.demo.service.AccountService;
import com.example.demo.service.CustomerService;
import com.example.demo.service.TransactionService;


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class HttpRequestTest {

	@Autowired
	private AccountService accountService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private TransactionService transactionService;

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

	private void assertGetQueryObject(String path, JSONObject expected) throws Exception {
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

	@BeforeEach
	void flushVirtualDatabase() {
		accountService.clear();
		customerService.clear();
		transactionService.clear();
	}

	@Test
	void customersEmpty() throws Exception {
		JSONArray expected = new JSONArray("[]");
		assertGetQueryArray("/api/customers", expected);
	}

	@Test
	void accountsEmpty() throws Exception {
		JSONArray expected = new JSONArray("[]");
		assertGetQueryArray("/api/accounts", expected);
	}

	@Test
	void transactionsEmpty() throws Exception {
		JSONArray expected = new JSONArray("[]");
		assertGetQueryArray("/api/transactions", expected);
	}

	@DirtiesContext
	@Test
	void customersAddOne() throws Exception {
		JSONObject payload = new JSONObject("{'name':'Alice', 'surname': 'Bright'}");
		JSONObject expected = new JSONObject("{'id':0,'name':'Alice','surname':'Bright'}");
		assertPostQueryObject("/api/customer", payload, expected);
	}

	@DirtiesContext
	@Test
	void accountsAddOneZeroBalance() throws Exception {
		customersAddOne();

		// The API will expose an endpoint which accepts the user information,
		// (customerID, initialCredit):
		JSONObject payloadAccount = new JSONObject("{'customerID':0, 'initialCredit': 0}");
		// Once the endpoint is called, a new account will be opened connected to the user
		// whose ID is customerID:
		JSONObject expectedAccount = new JSONObject("{'id':0,'customer':{'id':0,'name':'Alice','surname':'Bright'},'balance':0}");
		assertPostQueryObject("/api/account", payloadAccount, expectedAccount);

		// if initialCredit is not 0, a transaction will be sent to the new account:
		JSONArray expectedTransactions = new JSONArray("[]");
		assertGetQueryArray("/api/transactions", expectedTransactions);

		// Another Endpoint will output the user information showing Name, Surname,
		// balance, and transactions of the accounts:
		JSONObject expectedAccountInfo = new JSONObject("{'id':0,'customer':{'id':0,'name':'Alice','surname':'Bright'},'balance':0,'transactions':[]}");
		assertGetQueryObject("/api/account/0", expectedAccountInfo);
	}

	@DirtiesContext
	@Test
	void accountsAddOnePositiveBalance() throws Exception {
		customersAddOne();

		// The API will expose an endpoint which accepts the user information,
		// (customerID, initialCredit):
		JSONObject payloadAccount = new JSONObject("{'customerID':0, 'initialCredit': 3}");
		// Once the endpoint is called, a new account will be opened connected to the user
		// whose ID is customerID:
		JSONObject expectedAccount = new JSONObject("{'id':0,'customer':{'id':0,'name':'Alice','surname':'Bright'},'balance':3}");
		assertPostQueryObject("/api/account", payloadAccount, expectedAccount);

		// if initialCredit is not 0, a transaction will be sent to the new account:
		JSONArray expectedTransactions = new JSONArray("[{'id':0,'account':{'id':0,'customer':{'id':0,'name':'Alice','surname':'Bright'},'balance':3},'amount':3}]");
		assertGetQueryArray("/api/transactions", expectedTransactions);

		// Another Endpoint will output the user information showing Name, Surname,
		// balance, and transactions of the accounts:
		JSONObject expectedAccountInfo = new JSONObject("{'id':0,'customer':{'id':0,'name':'Alice','surname':'Bright'},'balance':3,'transactions':[{'id':0,'amount':3}]}");
		assertGetQueryObject("/api/account/0", expectedAccountInfo);
	}

	@DirtiesContext
	@Test
	void accountsAddOneNegativeBalance() throws Exception {
		customersAddOne();

		// The API will expose an endpoint which accepts the user information,
		// (customerID, initialCredit):
		JSONObject payloadAccount = new JSONObject("{'customerID':0, 'initialCredit': -1.20}");
		// Once the endpoint is called, a new account will be opened connected to the user
		// whose ID is customerID:
		JSONObject expectedAccount = new JSONObject("{'id':0,'customer':{'id':0,'name':'Alice','surname':'Bright'},'balance':-1.20}");
		assertPostQueryObject("/api/account", payloadAccount, expectedAccount);

		// if initialCredit is not 0, a transaction will be sent to the new account:
		JSONArray expectedTransactions = new JSONArray("[{'id':0,'account':{'id':0,'customer':{'id':0,'name':'Alice','surname':'Bright'},'balance':-1.20},'amount':-1.20}]");
		assertGetQueryArray("/api/transactions", expectedTransactions);

		// Another Endpoint will output the user information showing Name, Surname,
		// balance, and transactions of the accounts:
		JSONObject expectedAccountInfo = new JSONObject("{'id':0,'customer':{'id':0,'name':'Alice','surname':'Bright'},'balance':-1.20,'transactions':[{'id':0,'amount':-1.20}]}");
		assertGetQueryObject("/api/account/0", expectedAccountInfo);
	}

	@DirtiesContext
	@Test
	void transactionExtra() throws Exception {
		accountsAddOneNegativeBalance();

		JSONObject payloadTransaction = new JSONObject("{'accountID':0, 'amount': 5}");
		JSONObject expectedAccount = new JSONObject("{'id':1,'account':{'id':0,'customer':{'id':0,'name':'Alice','surname':'Bright'},'balance':3.80},'amount':5}");
		assertPostQueryObject("/api/transaction", payloadTransaction, expectedAccount);

		JSONArray expectedTransactions = new JSONArray("[{'id':0,'account':{'id':0,'customer':{'id':0,'name':'Alice','surname':'Bright'},'balance':3.80},'amount':-1.20},{'id':1,'account':{'id':0,'customer':{'id':0,'name':'Alice','surname':'Bright'},'balance':3.80},'amount':5}]");
		assertGetQueryArray("/api/transactions", expectedTransactions);

		// Another Endpoint will output the user information showing Name, Surname,
		// balance, and transactions of the accounts:
		JSONObject expectedAccountInfo = new JSONObject("{'id':0,'customer':{'id':0,'name':'Alice','surname':'Bright'},'balance':3.80,'transactions':[{'id':0,'amount':-1.20},{'id':1,'amount':5}]}");
		assertGetQueryObject("/api/account/0", expectedAccountInfo);
	}
}
