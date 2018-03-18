package twitterexamples;

import static io.restassured.RestAssured.given;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class TwitterE2EScenarioCRD {
	String consumerKey = "zdfQaULGDZZC58o3PVx9YQbHJ";
	String consumerSecret = "cvreoD6TK9aMSgk691eZP1uZLN817mwSMSbyjg0T2y26TR6gvg";
	String accessToken = "385493054-vpHu2F0aUWYkyUvZsQNyWuZa2Wf4XrhxdZDO1M2S";
	String accessTokenSecret = "wtxYZd6jrMh3gmT6tKHyDZi8jFTmwoSNtJsPwhB1lKWzf";
	String tweetID;
	
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
		
		tweetID = postATweetResponse.path("id_str");
	}
	
	@Test(dependsOnMethods= {"postingATweet"})
	public void verifyTextOfATweet() {
		Response tweet = 
		given()
			.auth()
			.oauth(consumerKey, consumerSecret, accessToken, accessTokenSecret)
			.param("id",tweetID)
		
		.when()
			.get("/show.json")
		.then()
			.statusCode(200)
			.and()
			.extract().response();
		
		String tweetText = tweet.path("text");
		System.out.println("The text of the tweet is "+ tweetText);
	}
	
	@Test(dependsOnMethods = {"verifyTextOfATweet"})
	public void deleteATweet(){
		given()
		.auth()
		.oauth(consumerKey, consumerSecret, accessToken, accessTokenSecret)
		.queryParam("id",tweetID)
	
		.when()
			.post("/destroy.json")
		.then()
			.statusCode(200);
			
	}
	
	//same previous test with pathParam
	@Test(dependsOnMethods = {"verifyTextOfATweet"})
	public void deleteATweetWithPathParam(){
		given()
		.auth()
		.oauth(consumerKey, consumerSecret, accessToken, accessTokenSecret)
		.pathParam("id",tweetID)
	
		.when()
		//forma de pasar un path parameter.
			.post("/destroy/{id}.json")
		.then()
		//se borro en el step previo
			.statusCode(404);
			
	}
}
