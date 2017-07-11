package testlibs;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.stubbing.Scenario;

import static io.restassured.RestAssured.given;
import org.junit.*;
import static org.hamcrest.Matchers.equalTo;


public class RestAssuredXmlWithWiremock {

	private static WireMockServer mockServer;

	@BeforeClass
	public static void setupServer() {
		mockServer = new WireMockServer();
		mockServer.start();

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
