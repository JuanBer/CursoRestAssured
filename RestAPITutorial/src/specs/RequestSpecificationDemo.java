package specs;

import static io.restassured.RestAssured.given;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.authentication.AuthenticationScheme;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;

public class RequestSpecificationDemo {
	String consumerKey = "zdfQaULGDZZC58o3PVx9YQbHJ";
	String consumerSecret = "cvreoD6TK9aMSgk691eZP1uZLN817mwSMSbyjg0T2y26TR6gvg";
	String accessToken = "385493054-vpHu2F0aUWYkyUvZsQNyWuZa2Wf4XrhxdZDO1M2S";
	String accessTokenSecret = "wtxYZd6jrMh3gmT6tKHyDZi8jFTmwoSNtJsPwhB1lKWzf";
	AuthenticationScheme authScheme = RestAssured.oauth(consumerKey, consumerSecret, accessToken, accessTokenSecret);

	RequestSpecBuilder requestBuilder;
	//using request specification code looks better
	static RequestSpecification requestSpect;
	
	@BeforeClass
	public void setup() {
		//built the request specification
		requestBuilder  = new RequestSpecBuilder();
		requestBuilder.setBaseUri("https://api.twitter.com");
		requestBuilder.setBasePath("/1.1/statuses");
		requestBuilder.addQueryParam("screen_name", "Juan Manuel Bertoni");
		requestBuilder.setAuth(authScheme);
		//add it to request specification
		requestSpect = requestBuilder.build();
	}
	
	@Test
	public void verifyHTTPResponse() {
		given()
			// only providing the request specification
			/*.auth()
			.oauth(consumerKey, consumerSecret, accessToken, accessTokenSecret)
			.param("screen_name","Juan Manuel Bertoni")*/
			.spec(requestSpect)
		
		.when()
			.get("/user_timeline.json")
		.then()
			.statusCode(200);
			
	}
}
