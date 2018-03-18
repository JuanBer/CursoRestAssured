package twitterexamples;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class TwitterGetRequest {
	String consumerKey = "zdfQaULGDZZC58o3PVx9YQbHJ";
	String consumerSecret = "cvreoD6TK9aMSgk691eZP1uZLN817mwSMSbyjg0T2y26TR6gvg";
	String accessToken = "385493054-vpHu2F0aUWYkyUvZsQNyWuZa2Wf4XrhxdZDO1M2S";
	String accessTokenSecret = "wtxYZd6jrMh3gmT6tKHyDZi8jFTmwoSNtJsPwhB1lKWzf";

	@BeforeClass
	public void setup() {
		RestAssured.baseURI = "https://api.twitter.com";
		RestAssured.basePath = "/1.1/statuses";
	}
	
	@Test
	public void verifyHTTPResponse() {
		given()
			.auth()
			.oauth(consumerKey, consumerSecret, accessToken, accessTokenSecret)
			.param("screen_name","Juan Manuel Bertoni")
		
		.when()
			.get("/user_timeline.json")
		.then()
			.statusCode(200);
			
	}
	
	@Test
	public void verifyUserNameIsExpected() {
		given()
			.auth()
			.oauth(consumerKey, consumerSecret, accessToken, accessTokenSecret)
			.param("screen_name","Juan Manuel Bertoni")
		
		.when()
			.get("/user_timeline.json")
		.then()
			.body("user.name", hasItem("Juan Manuel Bertoni"));
			
	}
}
