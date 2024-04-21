package utils;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ListUtilsTest {

    @Test
    public void divideEquallyListWithElementsGreaterThatDivisor() {
        final List<Integer> list = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9);
        final int divisor = 3;
        List<List<Integer>> dividedList = ListUtils.divideEqually(list, divisor);
        assertEquals(3, dividedList.size());
        assertEquals(List.of(1, 2, 3), dividedList.get(0));
        assertEquals(List.of(4, 5, 6), dividedList.get(1));
        assertEquals(List.of(7, 8, 9), dividedList.get(2));
    }

    @Test
    public void divideEquallyListWithElementsLessThatDivisor() {
        final List<Integer> list = List.of(1, 2, 3);
        final int divisor = 5;
        List<List<Integer>> dividedList = ListUtils.divideEqually(list, divisor);
        assertEquals(3, dividedList.size());
        assertEquals(List.of(1), dividedList.get(0));
        assertEquals(List.of(2), dividedList.get(1));
        assertEquals(List.of(3), dividedList.get(2));
    }

    @Test
    public void divideEquallyListWithElementsEqualToDivisor() {
        final List<Integer> list = List.of(1, 2, 3);
        final int divisor = 3;
        List<List<Integer>> dividedList = ListUtils.divideEqually(list, divisor);
        assertEquals(3, dividedList.size());
        assertEquals(List.of(1), dividedList.get(0));
        assertEquals(List.of(2), dividedList.get(1));
        assertEquals(List.of(3), dividedList.get(2));
    }

    @Test
    public void divideEquallyListWithElementsNotDivisibleByDivisor() {
        final List<Integer> list = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9);
        final int divisor = 4;
        List<List<Integer>> dividedList = ListUtils.divideEqually(list, divisor);
        assertEquals(4, dividedList.size());
        assertEquals(List.of(1, 2, 3), dividedList.get(0));
        assertEquals(List.of(4, 5), dividedList.get(1));
        assertEquals(List.of(6, 7), dividedList.get(2));
        assertEquals(List.of(8, 9), dividedList.get(3));
    }

    @Test
    public void divideEquallyListWithElementsNotDivisibleByDivisorHard() {
        final List<Integer> list = List.of(1, 2, 3, 4, 5, 6, 7);
        final int divisor = 4;
        List<List<Integer>> dividedList = ListUtils.divideEqually(list, divisor);
        assertEquals(4, dividedList.size());
        assertEquals(List.of(1, 2), dividedList.get(0));
        assertEquals(List.of(3, 4), dividedList.get(1));
        assertEquals(List.of(5, 6), dividedList.get(2));
        assertEquals(List.of(7), dividedList.get(3));
    }

}
