package junit5tests;

import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.*;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ParameterizedTests {

    //@ParameterizedTest()
    @ParameterizedTest(name = "Run: {index}  Value: {argumentsWithNames}")
    @NullAndEmptySource
    @ValueSource(strings = {"one", "two", "three"})
    void paramTestValueSource(String param) {
        // method can only have **1** parameter when using @ValueSource
        System.out.println("param: " + param);
    }


    @ParameterizedTest
    @CsvSource(value = {"pair1,28", "pair2,9", "'pa,ir3',3"})
    void paramTestCsvSource(String param1, int param2) {
        // can enclose string in single quotes if contains comma (default delimiter character)
        // or change delimiter char.  see next test
        System.out.println("param1: " + param1 + "  param2: " + param2);
    }

    @ParameterizedTest
    @CsvSource(value = {"Brees|Drew", "Manning|Archie"}, delimiter = '|')
    void paramTestCsvSourceDelimiter(String param1, String param2) {
        System.out.println("param1: " + param1 + "  param2: " + param2);
    }


    @ParameterizedTest
    @CsvFileSource(files =  "src/test/resources/params/players.csv", numLinesToSkip = 1)
    void csvFileSource(String name, String position, int num) {
        // https://junit.org/junit5/docs/current/api/org.junit.jupiter.params/org/junit/jupiter/params/provider/CsvFileSource.html
        System.out.println("Name: " + name + " Position: " +  position + " Number: " + num);
    }


    @ParameterizedTest
    // @MethodSource(value = "sourceString")
    @MethodSource(value = "sourceStringAsStream")
    void methodSource_String(String param1) {
        System.out.println("param1: " + param1);

    }

    @ParameterizedTest
    @MethodSource(value = "junit5tests.ParamProvider#sourceList_StringInt")
    void methodSource_StringInt(String param1, int param2) {
        System.out.println("param1: " + param1 + " param2: " + param2);

    }

    List<String> sourceString() {
        return Arrays.asList("brees", "kamara", "hill");
    }

    Stream<String> sourceStringAsStream() {
       return Stream.of("jordan", "lattimore", "williams");
    }

}
