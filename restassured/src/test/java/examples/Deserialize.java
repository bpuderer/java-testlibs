package examples;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import dataentities.Employee;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import static io.restassured.RestAssured.given;


public class Deserialize {

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(8089);

    @Test
    public void testXmlDeserialization() {

        Employee employee =

        given().
        when().
                get("http://localhost:8089/employee?format=xml").
        as(Employee.class);

        Assert.assertEquals("John Cleese", employee.getName());
    }

    @Test
    public void testJsonDeserialization() {

        Employee employee =

                given().
                        when().
                        get("http://localhost:8089/employee?format=json").
                        as(Employee.class);

        Assert.assertEquals("John Cleese", employee.getName());
    }

}
