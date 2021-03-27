package junit5tests;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIf;
import org.junit.jupiter.api.condition.DisabledIfSystemProperty;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.OS;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

public class DisableEnableTest {

    @Test
    @Disabled(value = "reason this test was disabled")
    void firstMethod() {
        System.out.println("Test #1");
    }

    @Test
    @DisabledOnOs(value = OS.LINUX, disabledReason = "too much win")
    void secondMethod() {
        System.out.println("Test #2");
    }

    @Test
    @DisabledIfSystemProperty(named = "env", matches = "staging", disabledReason = "demo")
    void thirdMethod() {
        System.out.println("Test #3");
    }

    @Test
    @DisabledIf(value = "provider", disabledReason = "demo @DisabledIf")
    void fourthMethod() {
        System.out.println("Test #4");
    }

    @Test
    void fifthMethod() {
        System.out.println("Test #5");
    }


    boolean provider() {
        return LocalDateTime.now().getDayOfWeek().equals(DayOfWeek.FRIDAY);
    }
}
