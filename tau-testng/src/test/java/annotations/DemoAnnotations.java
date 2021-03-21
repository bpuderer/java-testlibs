package annotations;

import org.testng.annotations.*;

public class DemoAnnotations {

    @BeforeSuite
    public void beforeSuite() {
        System.out.println("Chrome - set up system property");
    }

    @BeforeTest
    public void beforeTest() {
        System.out.println("Open chrome");
    }

    @BeforeClass
    public void beforeClass() {
        System.out.println("Open test application");
    }

    @BeforeMethod
    public void beforeMethod() {
        System.out.println("Sign in");
    }

    @Test
    public void searchCustomer() {
        System.out.println("Search for customer");
    }

    @Test
    public void searchProduct() {
        System.out.println("Search for product");
    }

    @AfterMethod
    public void afterMethod() {
        System.out.println("Sign out");
    }

    @AfterClass
    public void afterClass() {
        System.out.println("Close test after application");
    }

    @AfterTest
    public void afterTest() {
        System.out.println("Close chrome");
    }

    @AfterSuite
    public void afterSuite() {
        System.out.println("Chrome - cleanup cookies");
    }

}
