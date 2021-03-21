package dependencies;

import org.testng.Assert;
import org.testng.annotations.Test;

public class DependsOnMethods {


    @Test
    public void testFirst() {
        System.out.println("testFirst");
        Assert.fail();
    }

    @Test(dependsOnMethods = "testFirst")
    public void testSecond() {
        System.out.println("testSecond");
        // Assert.fail();
    }

    @Test
    public void testThird() {
        System.out.println("testThird");
        // Assert.fail();
    }

    @Test
    public void testFourth() {
        System.out.println("testFourth");
        // Assert.fail();
    }

    @Test(dependsOnMethods = {"testFirst", "testThird"})
    public void testFifth() {
        System.out.println("testFifth");
    }

}
