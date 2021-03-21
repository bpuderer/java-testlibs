package parameters;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class ParametersTest {

    // run from XML, not class
    @Test
    @Parameters({"URL", "BrowserType"})
    public void testParameters(String url, String browserType) {
        System.out.println(url + browserType);
    }

}
