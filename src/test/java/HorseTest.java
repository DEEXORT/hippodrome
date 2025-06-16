import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class HorseTest {

    @Test
    void constructor_ShouldThrowExceptionWhenNameIsNull() {
        Exception ex = assertThrows(IllegalArgumentException.class,
                () -> new Horse(null, 5.0, 5.0));
        assertEquals("Name cannot be null.", ex.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {" ", "\t", "\n"})
    void constructor_ShouldThrowExceptionWhenNameIsBlank(String value) {
        Exception exceptionBlank = assertThrows(IllegalArgumentException.class,
                () -> {
                    new Horse(value, 5.0, 5.0);
                });
        assertEquals("Name cannot be blank.", exceptionBlank.getMessage());
    }

    @Test
    void constructor_ShouldThrowExceptionWhenSpeedIsNegative() {
        Exception exceptionIncorrectSpeed = assertThrows(IllegalArgumentException.class,
                () -> new Horse("Koni", -5.0, 5.0));
        assertEquals("Speed cannot be negative.", exceptionIncorrectSpeed.getMessage());
    }

    @Test
    void constructor_ShouldThrowExceptionWhenDistanceIsNegative() {
        Exception exceptionIncorrectDistance = assertThrows(IllegalArgumentException.class,
                () -> new Horse("Koni", 5.0, -5.0));
        assertEquals("Distance cannot be negative.", exceptionIncorrectDistance.getMessage());
    }

    @Test
    void getName() {
        //given
        String expectedName = "Koni";
        Horse horse = new Horse(expectedName, 5.0, 5.0);

        //when
        String actualName = horse.getName();

        //then
        assertEquals(expectedName, actualName);
    }

    @Test
    void getSpeed() {
        //given
        double expectedSpeed = 5.0;
        Horse horse = new Horse("Koni", expectedSpeed, 5.0);

        //when
        double actualSpeed = horse.getSpeed();

        //then
        assertEquals(expectedSpeed, actualSpeed, 0.0001);
    }

    @Test
    void getDistance() {
        //given
        double expectedDistance = 5.0;
        Horse horse = new Horse("Koni", 5.0, expectedDistance);

        //when
        double actualDistance = horse.getDistance();

        //then
        assertEquals(expectedDistance, actualDistance, 0.0001);
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.4, 0.6, 0.2})
    void move(Double randomValue) {
        //given
        MockedStatic<Horse> horseMockedStatic = Mockito.mockStatic(Horse.class);
        horseMockedStatic.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(randomValue);
        double speed = 100.0;
        double distance = 50.0;
        Horse horse = new Horse("Koni", speed, distance);
        double expectedDistance = distance + (speed * randomValue);

        //when
        horse.move();
        double actualDistance = horse.getDistance();

        //then
        horseMockedStatic.verify(() -> Horse.getRandomDouble(0.2, 0.9), Mockito.times(1));
        assertEquals(expectedDistance, actualDistance, 0.0001);
        horseMockedStatic.close();
    }
}