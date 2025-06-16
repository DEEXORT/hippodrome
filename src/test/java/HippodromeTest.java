import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class HippodromeTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void constructor_ShouldThrowExceptionWhenInputIsNull() {
        Exception exceptionNull = assertThrows(IllegalArgumentException.class, () -> new Hippodrome(null));
        assertEquals("Horses cannot be null.", exceptionNull.getMessage());
    }

    @Test
    void constructor_ShouldThrowExceptionWhenInputIsEmpty() {
        Exception exceptionEmpty = assertThrows(IllegalArgumentException.class,
                () -> new Hippodrome(new ArrayList<>()));
        assertEquals("Horses cannot be empty.", exceptionEmpty.getMessage());
    }

    @Test
    void getHorses() {
        //given
        List<Horse> expectedHorseList = IntStream.range(0, 30)
                .mapToObj(i ->
                        new Horse("Konyashka" + i, Math.random() * 100, Math.random() * 50))
                .collect(Collectors.toList());

        //when
        Hippodrome hippodrome = new Hippodrome(expectedHorseList);
        List<Horse> actualHorseList = hippodrome.getHorses();

        //then
        for (int i = 0; i < 30; i++) {
            assertEquals(expectedHorseList.get(i), actualHorseList.get(i));
        }
    }

    @Test
    void move() {
        //given
        List<Horse> horseList = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            Horse mock = Mockito.mock(Horse.class);
            horseList.add(mock);
        }
        Hippodrome hippodrome = new Hippodrome(horseList);

        //when
        hippodrome.move();

        //then
        for (Horse horse : horseList) {
            Mockito.verify(horse, Mockito.only()).move();
        }
    }

    @Test
    void getWinner() {
        //given
        List<Horse> horseList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Horse mock = Mockito.mock(Horse.class);
            Mockito.when(mock.getDistance()).thenReturn((double) i);
            horseList.add(mock);
        }
        Collections.shuffle(horseList);
        Hippodrome hippodrome = new Hippodrome(horseList);

        //when
        Horse winner = hippodrome.getWinner();

        //then
        assertEquals(4, winner.getDistance());
    }
}