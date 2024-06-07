package examples;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;


public class BasicTests {

    @Test
    public void requestUsZipCode90210_checkPlaceNameInResponseBody_expectBeverlyHills() {
        given().
                log().all().  // log request
        when().
                get("http://zippopotam.us/us/90210").
        then().
                log().all().  // log response
                assertThat().
                statusCode(200).
                contentType(ContentType.JSON).
                // https://groovy-lang.org/processing-xml.html#_gpath
                // https://hamcrest.org/JavaHamcrest/javadoc/2.2/   Class Matchers
                body("places", hasSize(1)).
                body("places[0].'place name'", equalTo("Beverly Hills")).
                body("places.'place name'", hasItem("Beverly Hills"));
    }

    @Test
    public void testRandomChuckJokeNotEmpty(){
        given().
        when().
                get("https://api.chucknorris.io/jokes/random").
        then().
                statusCode(200).
                contentType(ContentType.JSON).
                body("value", not(is(emptyString())));
    }

    @Test
    public void testRandomChuckJokeJsonPath(){
        Response response = RestAssured.get("https://api.chucknorris.io/jokes/random");
        Assert.assertEquals(response.statusCode(), 200);
        JsonPath jp = new JsonPath(response.getBody().asString());
        Assert.assertEquals("https://assets.chucknorris.host/img/avatar/chuck-norris.png", jp.get("icon_url"));
    }

}
