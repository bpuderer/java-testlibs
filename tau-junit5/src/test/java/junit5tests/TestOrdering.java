package junit5tests;

import org.junit.jupiter.api.*;


// @TestMethodOrder(MethodOrderer.MethodName.class)
// @TestMethodOrder(MethodOrderer.DisplayName.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestOrdering {

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
    @Order(3)
    void testMethodB() {
        System.out.println("testMethodB");
    }

    @Test
    @Order(2)
    void testMethodA() {
        System.out.println("testMethodA");
    }

    @Test
    @Order(1)
    void testMethodC() {
        System.out.println("testMethodC");
    }

}
