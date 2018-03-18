package basic;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import model.PlacesAddModel;

import static org.hamcrest.Matchers.equalTo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PostRequestDemo {
	@BeforeClass
	private void setup() {
		RestAssured.baseURI = "https://maps.googleapis.com";
		RestAssured.basePath = "/maps/api";
	}
		
	@Test
	public void statusCodeVerification(){
		given()
			.queryParam("key","AIzaSyBYGoI57SsYICo9oQZgKxX0kejLrF9ZQeo")
			.body("{"
					  + "\"location\": {"
					  + "\"lat\": -33.8669710,"
					  + "\"lng\": 151.1958750"
					  + "},"
					  + "\"accuracy\": 50,"
					  + "\"name\": \"JuanBer Shoes!\","
					  + "\"phone_number\": \"(221) 645 3827\","
					  +	"\"address\": \"Calle 57 entre 11 y 12 número 836, CP 1900, La Plata, Buenos Aires, Argentina\","
					  +"\"types\": [\"shoe_store\"],"
					  + "\"website\": \"http://www.google.com.ar/\","
					  + "\"language\": \"en-AU\""
					  + "}")
		.when()
			.post("/place/add/json")
		.then()
			.statusCode(200)
			.and()
			.contentType(ContentType.JSON)
			.and()
			.body("status", equalTo("OK"));		
			
	}
	
	@Test
	public void statusCodeVerificationUsingModel(){
		Map<String, Double> location = new HashMap<String,Double>();
		location.put("lat", -33.8669710);
		location.put("lng", 151.1958750);
		ArrayList<String> types = new ArrayList<String>();
		types.add("shoe_store");
		
		PlacesAddModel places = new PlacesAddModel();
		places.setLocation(location);
		places.setAccuracy(50);
		places.setName("JuanBer Shoes");
		places.setPhoneNumber("(221) 645 3827");
		places.setAddress("Calle 57 entre 11 y 12 número 836, CP 1900, La Plata, Buenos Aires, Argentina");
		places.setTypes(types);
		places.setWebsite("http://www.google.com.ar");
		places.setLanguage("en-US");
		
		given()
			.queryParam("key","AIzaSyBYGoI57SsYICo9oQZgKxX0kejLrF9ZQeo")
		//jackson or gson --->>> convert the object to json format	
			.body(places)
		.when()
			.post("/place/add/json")
		.then()
			.statusCode(200)
			.and()
			.contentType(ContentType.JSON)
			.and()
			.body("status", equalTo("OK"));		
			
	}
}
