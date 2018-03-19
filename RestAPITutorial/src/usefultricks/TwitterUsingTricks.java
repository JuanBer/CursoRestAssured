package usefultricks;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import java.util.concurrent.TimeUnit;

public class TwitterUsingTricks {
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
	public void verifyResponse() {
		given()
			.auth()
			.oauth(consumerKey, consumerSecret, accessToken, accessTokenSecret)
			.param("screen_name","Juan Manuel Bertoni")
		
		.when()
			.get("/user_timeline.json")
		.then()
			.log()
			.ifError()
			.statusCode(200)
			.and()
			.rootPath("[2].user")
			.body("screen_name",equalTo("jotaM1982"))
			.body("name",equalTo("Juan Manuel Bertoni"))
			.rootPath("[2].retweeted_status.user")
			.body("name", equalTo("Julia Mengolini"))
			.rootPath("[19].retweeted_status.user")
			.body("name", equalTo("Exa"));
	}
	
	@Test
	public void takeResponseTime() {
		long responseTime = 
		given()
			.auth()
			.oauth(consumerKey, consumerSecret, accessToken, accessTokenSecret)
			.param("screen_name","Juan Manuel Bertoni")
		
		.when()
			.get("/user_timeline.json")
			// devuelve el tiempo de respuesta
			.time();		
		
		System.out.println("Response time in milliseconds (default) " + responseTime);
	}
	
	@Test
	public void takeResponseTimeDefiningUnit() {
		long responseTime = 
		given()
			.auth()
			.oauth(consumerKey, consumerSecret, accessToken, accessTokenSecret)
			.param("screen_name","Juan Manuel Bertoni")
		
		.when()
			.get("/user_timeline.json")
			// devuelve el tiempo de respuesta
			.timeIn(TimeUnit.SECONDS);		
		
		System.out.println("Response time in seconds:  " + responseTime);
	}
	
	@Test
	public void verifyResponseTime() {
		given()
			.auth()
			.oauth(consumerKey, consumerSecret, accessToken, accessTokenSecret)
			.param("screen_name","Juan Manuel Bertoni")
		
		.when()
			.get("/user_timeline.json")
		.then()
			.log()
			.ifError()
			.statusCode(200)
			.and()
			// verificación del tiempo de respuesta
			.time(lessThan(900L), TimeUnit.MILLISECONDS);
	}

}
