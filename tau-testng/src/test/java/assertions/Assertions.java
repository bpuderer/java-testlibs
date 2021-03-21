package assertions;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class Assertions {

    SoftAssert softAssert = new SoftAssert();

    @Test
    public void hardAssertions() {
        // org.testng.AssertJUnit

        // org.testng.Assert
        // assertTrue, assertFalse
        // assertSame, assertNotSame.  Refer to same object
        // assertNotNull
        // assertEquals

        // TestNG - actual, expected, string
        // JUnit - string, expected, actual

        Assert.assertEquals(28, 28, "AE - Only displays for failure");
        Assert.assertFalse(true, "AF - Only displays for failure");
    }

    @Test
    public void softAssertions() {
        softAssert.assertFalse(true, "First failed soft assert");
        softAssert.assertEquals(28, 29, "Second failed soft assert");
        System.out.println("after two failed soft assertions");
        softAssert.assertAll();
    }

}
