package junit5tests;

import org.junit.jupiter.api.*;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertTrue;


public class MiscTests {

    @Test
    // @Timeout(5) // seconds
    @Timeout(value = 5, unit = TimeUnit.SECONDS)
    void assertTrueTest() throws InterruptedException {
        Thread.sleep(5100);
        assertTrue(true);  // assertFalse
    }


    @CustomAnnotation
    void customAnnotationTest() {
        System.out.println("test with custom annotation");
    }


    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @Nested
    class NestedTest {

        @BeforeAll
        void beforeAll() {
            System.out.println("in BeforeAll");
        }

        @Test
        void nestedTest() {
            System.out.println("nestedTest");
        }

    }

}
