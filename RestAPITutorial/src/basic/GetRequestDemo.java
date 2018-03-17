package basic;

import static io.restassured.RestAssured.given;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static org.hamcrest.Matchers.equalTo;

public class GetRequestDemo {
	
	/**
	 * Given I have this information
	 * When I perform this action
	 * Then this should be the output
	 */
	
	@BeforeClass
	public void setup() {
		RestAssured.baseURI = "https://maps.googleapis.com";
		RestAssured.basePath = "/maps/api";
	}
	
	@Test
	public void statusCodeVerification() {
		given()
			.param("units", "imperial")
			.param("origins", "Washington,DC")
			.param("destination", "New+York+City,NY")
			.param("key", "AIzaSyBYGoI57SsYICo9oQZgKxX0kejLrF9ZQeo")
		.when()
			.get("/distancematrix/json")
		.then()
			.statusCode(200);
	}
	
	@Test
	public void getResponseBody() {
		Response res = 
		given()
			.param("units", "imperial")
			.param("origins", "Washington,DC")
			.param("destinations", "New+York+City,NY")
			.param("key", "AIzaSyBYGoI57SsYICo9oQZgKxX0kejLrF9ZQeo")
		.when()
			.get("/distancematrix/json");
		
		System.out.println(res.body().asString());
	}
	
	@Test
	public void validateResponse() {
		given()
			.param("units", "imperial")
			.param("origins", "Washington,DC")
			.param("destinations", "New+York+City,NY")
			.param("key", "AIzaSyBYGoI57SsYICo9oQZgKxX0kejLrF9ZQeo")
		.when()
			.get("/distancematrix/json")
		.then()
			.statusCode(200)
			.and()
			.body("rows[0].elements[0].distance.text",equalTo("225 mi"))
			.contentType(ContentType.JSON);
		
		}
}
