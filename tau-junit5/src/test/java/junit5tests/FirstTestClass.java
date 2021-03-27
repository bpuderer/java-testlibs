package junit5tests;

import org.junit.jupiter.api.*;


// run from maven:
// mvn test -Dtest=AssertionsTest#assertEqualsTest
// mvn test -Dtest=AssertionsTest
// mvn test -Dtest=AssertionsTest,AssumptionsTest
// mvn test -DtestPackage=junit5tests
// mvn test -Dgroups=smoke

// https://junit.org/junit5/docs/current/api/org.junit.jupiter.api/org/junit/jupiter/api/BeforeAll.html
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FirstTestClass {

    @BeforeAll
    void setUpClass() {
        System.out.println("- before all");
    }

    @BeforeEach
    void setUp() {
        System.out.println("-- before each");
    }

    @AfterAll
    void tearDownClass() {
        System.out.println("- after all");
    }

    @AfterEach
    void tearDown() {
        System.out.println("-- after each");
    }

    @Test
    void firstMethod() {
        System.out.println("First test method");
    }

    @Test
    @DisplayName("Story123 - TC123 - second test method")
    void secondMethod() {
        System.out.println("Second test method");
    }

}
