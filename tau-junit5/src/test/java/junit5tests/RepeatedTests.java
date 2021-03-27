package junit5tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;

public class RepeatedTests {

    @RepeatedTest(3)
    void firstRepeatedTest() {
        System.out.println("firstRepeatedTest");
    }

    @RepeatedTest(value = 4, name = "repetition #{currentRepetition} out of {totalRepetitions}")
    @DisplayName("Repeated test example")
    void secondRepeatedTest() {
        System.out.println("secondRepeatedTest");
    }

    @RepeatedTest(3)
    void thirdRepeatedTest(RepetitionInfo repInfo) {
        // likely to combine with Assumptions
        System.out.println("Repetition #" + repInfo.getCurrentRepetition());
    }

}
