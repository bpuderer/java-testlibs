package dependencies;

import org.testng.Assert;
import org.testng.annotations.Test;

public class GroupsAttributes {


    @Test(groups = "initialize")
    public void testFirst() {
        System.out.println("testFirst");
        Assert.fail();
    }

    @Test(groups = {"smoke", "regression"} )
    public void testSecond() {
        System.out.println("testSecond");
        // Assert.fail();
    }

    @Test(dependsOnGroups = "initialize", groups = {"regression", "slow"} )
    public void testThird() {
        System.out.println("testThird");
        // Assert.fail();
    }

    @Test(dependsOnGroups = "initialize")
    public void testFourth() {
        System.out.println("testFourth");
        // Assert.fail();
    }

    @Test(dependsOnGroups = "initialize")
    public void testFifth() {
        System.out.println("testFifth");
    }

}
