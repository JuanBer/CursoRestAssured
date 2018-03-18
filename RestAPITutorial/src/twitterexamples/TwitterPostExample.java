package twitterexamples;

import static io.restassured.RestAssured.given;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class TwitterPostExample {
	String consumerKey = "zdfQaULGDZZC58o3PVx9YQbHJ";
	String consumerSecret = "cvreoD6TK9aMSgk691eZP1uZLN817mwSMSbyjg0T2y26TR6gvg";
	String accessToken = "385493054-vpHu2F0aUWYkyUvZsQNyWuZa2Wf4XrhxdZDO1M2S";
	String accessTokenSecret = "wtxYZd6jrMh3gmT6tKHyDZi8jFTmwoSNtJsPwhB1lKWzf";
	String id;

	@BeforeClass
	public void setup() {
		RestAssured.baseURI = "https://api.twitter.com";
		RestAssured.basePath = "/1.1/statuses";
	}

	@Test
	public void postingATweet() {

		Response postATweetResponse = 
			given()
				.auth()
				.oauth(consumerKey, consumerSecret, accessToken, accessTokenSecret)
				.queryParam("status", "Automatic tweet using restAssured")
			.when()
				.post("/update.json")
			.then()
				.statusCode(200)
				.extract().response();
		
		id = postATweetResponse.path("id_str");
		
		///otra forma de obtener el Json
		//al JsonPath le paso el objeto JAVA Response convertido a String
		JsonPath jsPath = new JsonPath(postATweetResponse.asString());
		System.out.println("User name of the tweeter account: " + jsPath.getString("user.name"));
	}
	
	@Test
	public void getATweetWithID() {
		given()
			.auth()
			.oauth(consumerKey, consumerSecret, accessToken, accessTokenSecret)
			.param("id","Juan Manuel Bertoni")
		
		.when()
			.get("/user_timeline.json")
		.then()
			.statusCode(200);
			
	}
}
