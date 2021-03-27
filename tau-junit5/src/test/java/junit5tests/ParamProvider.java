package junit5tests;

import org.junit.jupiter.params.provider.Arguments;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.params.provider.Arguments.arguments;

public class ParamProvider {

    static List<Arguments> sourceList_StringInt() {
        return Arrays.asList(arguments("brees", 9), arguments("kamara", 41));
    }
}
