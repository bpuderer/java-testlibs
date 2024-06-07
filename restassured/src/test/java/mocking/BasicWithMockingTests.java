package mocking;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.Rule;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;

public class BasicWithMockingTests {

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(8089);

    @Test
    public void testXmlResponse() {
        /*
        stub defined in resources/mappings/employees_xml.json.  filename doesn't matter
        also, givenThat and stubFor methods are equivalent below
        stubFor(get(urlEqualTo("/employees")).
                willReturn(aResponse().
                        withStatus(200).
                        withBodyFile("employees.xml").
                        withHeader("Content-Type", "application/xml")));
        */
        given().
        when().
                get("http://localhost:8089/employees").
        then().
                log().all().
                assertThat().
                body("employeeInfo.employee[0].name.@almaMater", equalTo("Downing College")).
                body("employeeInfo.employee[1].name", equalTo("Eric Idle")).
                body("employeeInfo.employee[-1].name", equalTo("Terry Gilliam")).
                body("employeeInfo.employee.findAll{it.country=='England'}.size()", equalTo(3)).
                body("employeeInfo.employee.findAll{it.country=='Canada'}", empty());
    }
}
