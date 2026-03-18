package com.devsuperior.dsmovie.controllers;

import com.devsuperior.dsmovie.tests.TokenUtil;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class MovieControllerRA {

	private Long existsMovieId, nonExistsMovieId;
	private String clientToken, adminToken, invalidToken;
	private String clientUsername, clientPassword, adminUsername, adminPassword;

	@BeforeEach
	public void setup() throws JSONException {
		baseURI = "http://localhost:8080";

		existsMovieId = 1L;
		nonExistsMovieId = 999L;

		clientUsername = "joaquim@gmail.com";
		clientPassword = "123456";

		adminUsername = "maria@gmail.com";
		adminPassword = "123456";

		clientToken = TokenUtil.obtainAccessToken(clientUsername,clientPassword);
		adminToken = TokenUtil.obtainAccessToken(adminUsername,adminPassword);
		invalidToken = adminToken + "xpto";
	}
	
	@Test
	public void findAllShouldReturnOkWhenMovieNoArgumentsGiven() {

		given()
				.when()
				.get("/movies")
				.then()
				.statusCode(200);
	}
	
	@Test
	public void findAllShouldReturnPagedMoviesWhenMovieTitleParamIsNotEmpty() {
		given()
				.queryParam("title", "Matrix")
				.when()
				.get("/movies")
				.then()
				.statusCode(200);
	}
	
	@Test
	public void findByIdShouldReturnMovieWhenIdExists() {
		given()
				.when()
				.get("/movies/{id}", existsMovieId)
				.then()
				.statusCode(200);
	}
	
	@Test
	public void findByIdShouldReturnNotFoundWhenIdDoesNotExist() {
		given()
				.when()
				.get("/movies/{id}", nonExistsMovieId)
				.then()
				.statusCode(404);
	}
	
	@Test
	public void insertShouldReturnUnprocessableEntityWhenAdminLoggedAndBlankTitle() throws JSONException {		
	}
	
	@Test
	public void insertShouldReturnForbiddenWhenClientLogged() throws Exception {
	}
	
	@Test
	public void insertShouldReturnUnauthorizedWhenInvalidToken() throws Exception {
	}
}
