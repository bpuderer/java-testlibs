package testlibs;

import static io.restassured.RestAssured.given;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import org.junit.*;

import static org.hamcrest.Matchers.*;


public class RestAssuredChuckJokeTests {

	@Test
	public void testRandomChuckJokeEqualTo(){
		given().
		when().
			get("http://api.icndb.com/jokes/random").
		then().
			statusCode(200).
			contentType(ContentType.JSON).
			body("type", equalTo("success")).
			body("value.joke", not(isEmptyString()));
	}

	@Test
	public void testRandomChuckJokeJsonPath(){
		Response response = RestAssured.get("http://api.icndb.com/jokes/random");
		Assert.assertEquals(response.statusCode(), 200);
		JsonPath jp = new JsonPath(response.getBody().asString());
		Assert.assertEquals("success", jp.get("type"));
	}

}
