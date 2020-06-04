package examples;

import com.tngtech.java.junit.dataprovider.*;
import io.restassured.http.ContentType;
import org.junit.Test;
import org.junit.runner.RunWith;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;


@RunWith(DataProviderRunner.class)
public class Parameterize {

    // DataProvider built into TestNG, but not JUnit4
    @DataProvider
    public static Object[][] zipCodesAndPlaces() {
        return new Object[][] {
                {"us", "90210", "Beverly Hills"},
                {"us", "12345", "Schenectady"},
                {"ca", "B2R", "Waverley"}
        };
    }

    @Test
    @UseDataProvider("zipCodesAndPlaces")
    public void requestZipCode_checkPlaceNameInResponseBody(String countryCode, String zipCode, String expectedPlaceName) {
        given().
                pathParam("countryCode", countryCode).
                pathParam("zipCode", zipCode).
        when().
                get("http://zippopotam.us/{countryCode}/{zipCode}").
        then().
                assertThat().
                body("places[0].'place name'", equalTo(expectedPlaceName));
    }

}
