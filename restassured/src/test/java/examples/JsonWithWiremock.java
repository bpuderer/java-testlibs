package examples;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import com.github.tomakehurst.wiremock.WireMockServer;

import com.github.tomakehurst.wiremock.client.WireMock;
import dataentities.Message;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.get;
import org.junit.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.lessThan;

import java.util.HashMap;
import java.util.Map;

public class JsonWithWiremock {

	private static WireMockServer mockServer;

	@BeforeClass
	public static void setupServer() {
		mockServer = new WireMockServer();
		mockServer.start();

		stubFor(WireMock.get(urlEqualTo("/employees")).
				willReturn(aResponse().
						withStatus(200).
						withBodyFile("employees.xml").
						withHeader("Content-Type", "application/xml")));

		stubFor(WireMock.get(urlEqualTo("/demo")).willReturn(aResponse()
				.withStatus(200)
				.withHeader("Content-Type", "application/json")
				.withBody("{\"identifier\":{\"ISBN-10\":\"0374530874\"},\"title\":\"The Violent Bear It Away\",\"nextBook\":2}")));

		stubFor(WireMock.get(urlEqualTo("/demo/2")).willReturn(aResponse()
				.withStatus(200)
				.withHeader("Content-Type", "application/json")
				.withBody("{\"identifier\":{\"ISBN-10\":\"0374530637\"},\"title\":\"Wise Blood\",\"nextBook\":3}")));

		stubFor(WireMock.get(urlEqualTo("/books")).willReturn(aResponse()
				.withStatus(200)
				.withHeader("Content-Type", "application/json")
				.withBody("{\"books\":[{\"price\":2,\"title\":\"$2 Title\"},{\"price\":1,\"title\":\"One Dollar Title\"},{\"price\":3,\"title\":\"3 Dollar Title\"}]}")));

		stubFor(WireMock.get(urlEqualTo("/msg")).willReturn(aResponse()
				.withStatus(200)
				.withHeader("Content-Type", "application/json")
				.withBody("{\"message\":\"running\"}")));

		stubFor(post(urlEqualTo("/msg"))
				.withHeader("Content-Type", containing("json"))
				.willReturn(aResponse()
				.withStatus(204)));
		
		stubFor(WireMock.get(urlEqualTo("/anonymous")).willReturn(aResponse()
				.withStatus(200)
				.withHeader("Content-Type", "application/json")
				.withBody("[1, 2]")));
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
			// param("name", "value1", "value2").
			// param("name").
			// formParam("name", "value").
			// queryParam("name", "value").
			// header("TestId", "testExamplePathParam").
			// pathParam("id", 2).
			spec(spec).
		when().
			get("/demo/{id}").
			// get("/demo/{id}", 2).
		then().
			time(lessThan(300L)). // ms
			statusCode(200).
			header("Content-Type", equalTo(ContentType.JSON.toString())).
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
			body("books.price", hasItems(1, 2, 3)).
			body("books.price[0]", equalTo(2)).
			body("books.size()", is(3)).
			body("books.price.collect { it }.sum()", lessThan(10)).
			// *. Groovy spread operator - calling the action on each item and collecting the result into a list
			body("books.title*.length().sum()", lessThan(40)).
			body("books.findAll { it.price > 2 }.title", hasItems("3 Dollar Title"));
	}

	@Test
	public void testObjectResponse() {
		Message msg = get("/msg").as(Message.class);
		Assert.assertEquals("running", msg.getMessage());
	}

	@Test
	public void testObjectRequest() {
		given().
			contentType(ContentType.JSON).
			// body(new Message("requesting")).
			body(new Message().setMessage("requesting")).
		when().
			post("/msg").
		then().
			statusCode(204);
	}

	@Test
	public void testHashMapRequest() {
		Map<String, Object> jsonAsMap = new HashMap<>();
		jsonAsMap.put("message", "requesting");

		given().
			contentType(ContentType.JSON).
			// body(new Message("requesting")).
			body(jsonAsMap).
		when().
			post("/msg").
		then().
			statusCode(204);
	}

	@Test
	public void testLogRequestResponse() {
		given().
			// log().all().
			log().ifValidationFails().
		when().
			get("/idontexist").
		then().
			// log().all().
			log().ifValidationFails().
			statusCode(200);
	}

	@Test
	public void testAnonymousRoot() {
		given().
		when().
			get("/anonymous").
		then().
			body("$", hasItems(1, 2)).
			statusCode(200);
	}

	@AfterClass
	public static void tearDown(){
		mockServer.stop();
	}

}
