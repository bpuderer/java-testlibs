package testlibs;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import org.junit.Assert;
import org.junit.Test;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.equalTo;


public class RestAssuredChuckJokeTests {

	@Test
    public void testRandomChuckJokeEqualTo(){
        RestAssured.get("http://api.icndb.com/jokes/random").then().assertThat().statusCode(200).and().contentType(JSON).and().body("type",
        		equalTo("success"));
    }

    @Test
    public void testRandomChuckJokeJsonPath(){
    	Response response = RestAssured.get("http://api.icndb.com/jokes/random").andReturn();
    	JsonPath jsonPath = new JsonPath(response.getBody().asString());
    	Assert.assertEquals("success", jsonPath.getString("type"));
    }

    
    private class Value {
    	int id;
    	String joke;
    	String[] categories;
    }
    
    private class JokeResponse {
    	String type;
    	Value value;
    }
    
    @Test
    public void testRandomChuckJokeObject(){
    	Response response = RestAssured.get("http://api.icndb.com/jokes/random").andReturn();
    	JokeResponse joke = new JsonPath(response.getBody().asString()).getObject("$", JokeResponse.class);
    	Assert.assertEquals("success", joke.type);
    	Assert.assertNotNull(joke.value.joke);
    }   
    
}
