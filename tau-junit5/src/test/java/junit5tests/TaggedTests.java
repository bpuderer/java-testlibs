package junit5tests;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

public class TaggedTests {

    @Test
    @Tag("smoke")
    void firstMethod() {
        System.out.println("Test #1");
    }

    @Test
    @Tag("slow")
    void secondMethod() {
        System.out.println("Test #2");
    }

    @Test
    @Tag("smoke")
    @Tag("slow")
    void thirdMethod() {
        System.out.println("Test #3");
    }

    @Test
    @Tag("smoke")
    @Tag("acceptance")
    void fourthMethod() {
        System.out.println("Test #4");
    }

    @Test
    void fifthMethod() {
        System.out.println("Test #5");
    }

}
