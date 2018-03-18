package basic;

import static io.restassured.RestAssured.given;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import static org.hamcrest.Matchers.equalTo;

public class GetRequestDemoXMLResponse {
	
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
	public void statusCodeVerificationXML() {
		given()
			.param("units", "imperial")
			.param("origins", "Washington,DC")
			.param("destination", "New+York+City,NY")
			.param("key", "AIzaSyBYGoI57SsYICo9oQZgKxX0kejLrF9ZQeo")
		.when()
			.get("/distancematrix/xml")
		.then()
			.statusCode(200);
	}
	
	@Test
	public void getResponseBodyXML() {
		Response res = 
		given()
			.param("units", "imperial")
			.param("origins", "Washington,DC")
			.param("destinations", "New+York+City,NY")
			.param("key", "AIzaSyBYGoI57SsYICo9oQZgKxX0kejLrF9ZQeo")
		.when()
			.get("/distancematrix/xml");
		
		System.out.println(res.body().asString());
	}
	
	@Test
	public void validateResponseXML() {
		given()
			.param("units", "imperial")
			.param("origins", "Washington,DC")
			.param("destinations", "New+York+City,NY")
			.param("key", "AIzaSyBYGoI57SsYICo9oQZgKxX0kejLrF9ZQeo")
		.when()
			.get("/distancematrix/xml")
		.then()
			.statusCode(200)
			.and()
			.body("distancematrixresponse.row.element.distance.text",equalTo("225 mi"))
			.contentType(ContentType.XML);
		
		}
	
	@Test
	public void validateResponseXML_2() {
		Response xmlResponse = 
		given()
			.param("units", "imperial")
			.param("origins", "Washington,DC")
			.param("destinations", "New+York+City,NY")
			.param("key", "AIzaSyBYGoI57SsYICo9oQZgKxX0kejLrF9ZQeo")
		.when()
			.get("/distancematrix/xml")
		.then()
			.statusCode(200)
			.and()
			.body("distancematrixresponse.row.element.distance.text",equalTo("225 mi"))
			.contentType(ContentType.XML)
			.and()
			.extract().response();
	
		XmlPath xmlPath = new XmlPath(xmlResponse.asString());
		System.out.println("Trip duration: " + xmlPath.get("distancematrixresponse.row.element.duration.text"));
		
		}
}
