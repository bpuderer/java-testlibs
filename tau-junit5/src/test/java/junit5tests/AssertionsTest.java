package junit5tests;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

public class AssertionsTest {

    @Test
    void assertEqualsTest() {
        // assertNotEquals
        assertEquals("expected", "actual", "strings don't match");
    }

    @Test
    void assertEqualsListsTest()
    {
        List<String> expectedVals = Arrays.asList("first", "second");
        List<String> actualVals = Arrays.asList("first", "second");
        List<String> actualValsDiffOrder = Arrays.asList("second", "first");

        assertEquals(expectedVals, actualVals);
        assertEquals(expectedVals, actualValsDiffOrder);  // fails
    }

    @Test
    void assertArrayEqualsTest() {
        int[] expectedVals = {1, 2, 3};
        int[] actualVals = {1, 2, 3};
        int[] actualValsDiffOrder = {1, 3, 2};
        assertArrayEquals(expectedVals, actualVals);
        assertArrayEquals(expectedVals, actualValsDiffOrder); // fails
    }

    @Test
    void assertTrueTest() {
        assertTrue(true);  // assertFalse
    }

    @Test
    void assertThrowsTest() {
        assertThrows(NullPointerException.class, null);
    }

    @Test
    void assertAllTest() {
        assertAll(
                () -> assertEquals("expected", "actual"),
                () -> assertTrue(true),
                () -> assertTrue(false)
        );
    }

    // Hamcrest - assertion matchers
    // Hamcrest is an anagram for "matchers"
    @Test
    void assertThatMapTest() {
        Map<String, Integer> theMap = new HashMap<>();
        theMap.put("firstKey", 1);
        theMap.put("secondKey", 2);

        assertThat(theMap, hasKey("secondKey"));
        assertThat(theMap, hasValue(1));
    }

    @Test
    void assertThatListTest() {
        List<String> theList = Arrays.asList("one", "two", "three");
        assertThat(theList, hasItem("two"));
    }

    @Test
    void assertAnyOfAllOfTest() {
        List<String> theList = Arrays.asList("one", "two", "three");

        assertThat(theList, anyOf(hasItem("not in list"),
                hasItem("two")));
        assertThat(theList, allOf(hasItem("three"),
                hasItem("two")));
    }

    @Test
    void assertContainsAnyOrderTest() {
        List<String> theList = Arrays.asList("one", "two", "three");

        assertThat(theList, containsInAnyOrder("one", "three", "two"));
    }

    @Test
    void assertContainsAnyOrderTwoLists() {
        List<String> firstList = Arrays.asList("one", "two", "three");
        List<String> secondList = Arrays.asList("three", "two", "one");

        assertThat(firstList, Matchers.containsInAnyOrder(secondList.toArray()));
    }

}
