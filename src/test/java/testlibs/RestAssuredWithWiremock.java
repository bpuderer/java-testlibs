package testlibs;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.stubbing.Scenario;

import static io.restassured.RestAssured.given;
import org.junit.*;
import static org.hamcrest.Matchers.equalTo;


public class RestAssuredWithWiremock {

	private static WireMockServer mockServer;

	@BeforeClass
	public static void setupServer() {
		mockServer = new WireMockServer();
        mockServer.start();
        
		stubFor(get(urlEqualTo("/demo")).willReturn(aResponse()
				.withStatus(200)
				.withHeader("Content-Type", "application/json")
				.withBody("{\"value\":0}")));

		stubFor(get(urlEqualTo("/demo/2")).willReturn(aResponse()
				.withStatus(200)
				.withHeader("Content-Type", "application/json")
				.withBody("{\"value\":2}")));
		
		// stateful
		stubFor(get(urlEqualTo("/login"))
				.inScenario("login")
				.whenScenarioStateIs(Scenario.STARTED)
				.willSetStateTo("loggedIn")
				.willReturn(aResponse()
				    .withStatus(200)
				    .withHeader("Content-Type", "application/xml")
				    .withBody("<message>login successful</message>")));
		
		stubFor(get(urlEqualTo("/login"))
				.inScenario("login")
				.whenScenarioStateIs("loggedIn")
				.willSetStateTo("loggedIn")
				.willReturn(aResponse()
				    .withStatus(200)
				    .withHeader("Content-Type", "application/xml")
				    .withBody("<message>already logged in</message>")));
	}

	@Test
	public void testExampleStatusCodeResponse() {
		given().
		when().
		    get("/demo").
		then().
		    statusCode(200).
		    body("value", equalTo(0));
	}

	@Test
	public void testExamplePathParam() {
		given().
		    // .param("name", "value")
		    // .header("TestId", "testExamplePathParam")
		    pathParam("id", 2).
		when().
		    get("/demo/{id}").
		then().
		    statusCode(200).
		    body("value", equalTo(2));
	}
	
	@Test
	public void testLogin() {
		given().
		when().
		    get("/login").
		then().
		    statusCode(200).
		    body("message", equalTo("login successful"));
		
		given().
		when().
		    get("/login").
		then().
		    statusCode(200).
		    body("message", equalTo("already logged in"));		
	}
	
    @AfterClass
    public static void tearDown(){
        mockServer.stop();
    }
	
}