package annotations;

import org.testng.annotations.Test;

public class Priority {

    // defaults to alphabetical order without priority attribute

    @Test(priority=1)
    public void testFirst() {
        System.out.println("testFirst");
    }

    @Test(priority=2)
    public void testSecond() {
        System.out.println("testSecond");
    }

    @Test(priority=3)
    public void testThird() {
        System.out.println("testThird");
    }

    @Test(priority=4)
    public void testFourth() {
        System.out.println("testFourth");
    }

}
