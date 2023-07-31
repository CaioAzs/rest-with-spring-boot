package com.restspringboot.azsrest.integrationtests.controller.withxml;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.restspringboot.azsrest.configs.TestConfigs;
import com.restspringboot.azsrest.integrationtests.testcontainers.AbstractIntegrationTest;
import com.restspringboot.azsrest.integrationtests.vo.AccountCredentialsVO;
import com.restspringboot.azsrest.integrationtests.vo.TokenVO;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)
public class AuthControllerXMLTest extends AbstractIntegrationTest {

	private static TokenVO tokenVO;

	@Test
	@Order(1)
	public void testSignin() throws JsonMappingException, JsonProcessingException {

		AccountCredentialsVO user = new AccountCredentialsVO("Caio", "admin123");

		tokenVO = given()
				.basePath("/auth/signin")
				.port(TestConfigs.SERVER_PORT)
				.contentType(TestConfigs.CONTENT_TYPE_XML)
				.body(user)
				.when()
				.post()
				.then()
				.statusCode(200)
				.extract()
				.body()
				.as(TokenVO.class);

		assertNotNull(tokenVO.getAccessToken());
		assertNotNull(tokenVO.getRefreshToken());
	}

	@Test
	@Order(2)
	public void testRefresh() throws JsonMappingException, JsonProcessingException {

		var newTokenVO = given()
				.basePath("/auth/refresh")
				.port(TestConfigs.SERVER_PORT)
				.contentType(TestConfigs.CONTENT_TYPE_XML)
				.pathParam("username", tokenVO.getUsername())
				.header(TestConfigs.HEADER_PARAM_AUTHORIZATION, "Bearer " + tokenVO.getRefreshToken())
				.when()
				.put("{username}")
				.then()
				.statusCode(200)
				.extract()
				.body()
				.as(TokenVO.class);

		assertNotNull(newTokenVO.getAccessToken());
		assertNotNull(newTokenVO.getRefreshToken());
	}
}
