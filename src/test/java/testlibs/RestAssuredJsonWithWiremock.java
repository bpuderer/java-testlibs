package testlibs;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import com.github.tomakehurst.wiremock.WireMockServer;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.given;
import org.junit.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.lessThan;

public class RestAssuredJsonWithWiremock {

	private static WireMockServer mockServer;

	@BeforeClass
	public static void setupServer() {
		mockServer = new WireMockServer();
        mockServer.start();
        
		stubFor(get(urlEqualTo("/demo")).willReturn(aResponse()
				.withStatus(200)
				.withHeader("Content-Type", "application/json")
				.withBody("{\"identifier\":{\"ISBN-10\":\"0374530874\"},\"title\":\"The Violent Bear It Away\",\"nextBook\":2}")));

		stubFor(get(urlEqualTo("/demo/2")).willReturn(aResponse()
				.withStatus(200)
				.withHeader("Content-Type", "application/json")
				.withBody("{\"identifier\":{\"ISBN-10\":\"0374530637\"},\"title\":\"Wise Blood\",\"nextBook\":3}")));
		
		stubFor(get(urlEqualTo("/books")).willReturn(aResponse()
				.withStatus(200)
				.withHeader("Content-Type", "application/json")
				.withBody("{\"books\":[{\"price\":2,\"title\":\"$2 Title\"},{\"price\":1,\"title\":\"One Dollar Title\"},{\"price\":3,\"title\":\"3 Dollar Title\"}]}")));
	}

	@Test
	public void testExampleStatusCodeResponse() {
		// can use specs when tests assert same response or use same request data
		// move init into @BeforeClass
		ResponseSpecification spec = new ResponseSpecBuilder().expectStatusCode(200)
				.expectBody("identifier.ISBN-10", equalTo("0374530874"))
				.expectBody("title", equalTo("The Violent Bear It Away")).build();
		
		given().
		when().
		    get("/demo").
		then().
		    spec(spec);
	}

	@Test
	public void testExamplePathParam() {
		RequestSpecification spec = new RequestSpecBuilder().addPathParam("id", 2).build(); 
		
		given().
		    // param("name", "value").
		    // header("TestId", "testExamplePathParam").
		    // pathParam("id", 2).
		    spec(spec).
		when().
		    get("/demo/{id}").
		then().
		    statusCode(200).
		    body("title", equalTo("Wise Blood"));
	}

	@Test
	public void testExtract() {
		int nextBookId = 
		given().
		when().
		    get("/demo").
		then().
		    statusCode(200).
		extract().
		    path("nextBook");
		
		given().
	        pathParam("id", nextBookId).
        when().
            get("/demo/{id}").
        then().
            statusCode(200).
            body("title", equalTo("Wise Blood"));
	}
	
	@Test
	public void testWithGroovyCollectionClosure() {
		// http://docs.groovy-lang.org/latest/html/groovy-jdk/java/util/Collection.html
		given().
		when().
		    get("/books").
		then().
		    statusCode(200).
		    body("books.price.collect { it }.sum()", lessThan(10)).
		    // *. Groovy spread operator - calling the action on each item and collecting the result into a list
		    body("books.title*.length().sum()", lessThan(40)).
		    body("books.findAll { it.price > 2 }.title", hasItems("3 Dollar Title"));
	}
	
    @AfterClass
    public static void tearDown(){
        mockServer.stop();
    }
	
}