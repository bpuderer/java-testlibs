package junit5tests;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assumptions.*;


public class AssumptionsTest {

    @ParameterizedTest(name = "Run: {index}  Value: {argumentsWithNames}")
    @ValueSource(ints = {1, 2, 5})
    void intValues(int param) {
        // skip based on boolean condition
        assumeTrue(param > 3, "param 3 or less. skipping");
        System.out.println("param: " + param);
    }


    @ParameterizedTest
    @CsvSource(value = {"pair1,28", "pair2,9", "pair3,3"})
    void paramTestCsvSource(String param1, int param2) {
        // assumingThat is independent from rest of code in test
        assumingThat(param2 > 5, () -> System.out.println("executable ran"));
        System.out.println("param1: " + param1 + "  param2: " + param2);
    }


}
