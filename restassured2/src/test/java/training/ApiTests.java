package training;

import io.restassured.http.ContentType;
import models.Planet;
import models.Product;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.List;
import java.util.ArrayList;


public class ApiTests {

    // covered: request url, payload including serialization, get/post/put/delete, query param
    // matcher on response  https://hamcrest.org/JavaHamcrest/javadoc/1.3/org/hamcrest/Matchers.html
    // asserting parts of json, response code, response header,

    @Test
    public void getPlanetAssertPartOfJsonResponse() {
        // given when then
        String endpoint = "https://swapi.dev/api/planets/1/";
        given().
                log().all().response().
        when().
                get(endpoint).
        then().
                log().all().
                assertThat().
                statusCode(200).
                contentType(ContentType.JSON).
                header("Content-Type", equalTo("application/json")).
                body("name", equalTo("Tatooine")).
                body("residents[0]", equalTo("https://swapi.dev/api/people/1/")).
                body("residents.size()", equalTo(10)).
                body("films", everyItem(containsString("films")));
    }


    @Test
    public void getPlanetAssertDeserializedBody() {
        String endpoint = "https://swapi.dev/api/planets/1/";

        List<String> people = new ArrayList<>();
        people.add("https://swapi.dev/api/people/1/");
        people.add("https://swapi.dev/api/people/2/");
        people.add("https://swapi.dev/api/people/4/");
        people.add("https://swapi.dev/api/people/6/");
        people.add("https://swapi.dev/api/people/7/");
        people.add("https://swapi.dev/api/people/8/");
        people.add("https://swapi.dev/api/people/9/");
        people.add("https://swapi.dev/api/people/11/");
        people.add("https://swapi.dev/api/people/43/");
        people.add("https://swapi.dev/api/people/62/");

        List<String> films = new ArrayList<>();
        films.add("https://swapi.dev/api/films/1/");
        films.add("https://swapi.dev/api/films/3/");
        films.add("https://swapi.dev/api/films/4/");
        films.add("https://swapi.dev/api/films/5/");
        films.add("https://swapi.dev/api/films/6/");

        Planet expectedPlanet = new Planet(
                "Tatooine",
                "23",
                "304",
                "10465",
                "arid",
                "1 standard",
                "desert",
                "1",
                "200000",
                people,
                films,
                "2014-12-09T13:50:49.641000Z",
                "2014-12-20T20:58:18.411000Z",
                "https://swapi.dev/api/planets/1/"
        );

        Planet actualPlanet =
                given().
                    log().all().
                when().
                    get(endpoint).
                    as(Planet.class);

        assertThat(actualPlanet, samePropertyValuesAs(expectedPlanet));
    }

    @Test
    public void getPlanetUselessQueryParam() {
        String endpoint = "https://swapi.dev/api/planets/1/";
        given().
                queryParam("id", 2).
                log().all().
        when().
                get(endpoint).
        then().
                log().all().
                assertThat().
                statusCode(200);
    }

    @Test
    public void getPlanetUselessIncorrectBody() {
        String endpoint = "https://swapi.dev/api/planets/1/";
        String body = """
                {
                "id": 7,
                "name": "blahblah"
                }
                """;
        // requires jackson databind
        Product product = new Product(8, "blahblahblah");

        given().
                //body(body).
                body(product).    // serializing
                log().all().
        when().
                get(endpoint).
                //post(endpoint).
        then().
                log().all();
    }
}
