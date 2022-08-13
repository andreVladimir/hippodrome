import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;

import java.io.FileReader;
import java.lang.reflect.Field;

public class HorseTest {
    @Test
    public void nullHorseNameException () {
        assertThrows(IllegalArgumentException.class, () -> new Horse(null, 1));
    }

    @Test
    public void nullHorseNameExceptionMessage() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> new Horse(null, 1));
        assertEquals("Name cannot be null.", thrown.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {""," ", "  "})
    public void emptyArgException(String argument) {
        assertThrows(IllegalArgumentException.class, () -> new Horse(argument, 1));
    }

    @ParameterizedTest
    @ValueSource(strings = {""," ", "  "})
    public  void emptyNameExceptionMessage(String argument) {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> new Horse(argument, 1));
        assertEquals("Name cannot be blank.", thrown.getMessage());
    }

    @Test
    public void negativeSpeedException() {
        assertThrows(IllegalArgumentException.class, () -> new Horse("joe", -1));
    }

     @Test
    public void negativeSpeedExceptionMessage() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> new Horse("joe", -1));
        assertEquals("Speed cannot be negative.", thrown.getMessage());
     }

     @Test
    public void negativeDistanceException() {
        assertThrows(IllegalArgumentException.class, () -> new Horse("joe", 1, -1));
     }

     @Test
    public void negativeDistanceExceptionMessage() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> new Horse("joe", 1, -1));
        assertEquals("Distance cannot be negative.", thrown.getMessage());
     }

     @Test
     // there are other ways to do this test but wanted to use reflection API
    public void getName() throws NoSuchFieldException, IllegalAccessException {
        Horse horse = new Horse("joe", 1,1);

        Field name = Horse.class.getDeclaredField("name");
        String nameValue = (String) name.get(horse);
        assertEquals("joe", nameValue);
     }

     @Test
    public void getSpeed() throws NoSuchFieldException, IllegalAccessException {
        Horse horse = new Horse("joe", 1.1, 2);

        Field speed = Horse.class.getDeclaredField("speed");
        double speedValue = (double) speed.get(horse);
        assertEquals(1.1, speedValue);
     }

     @Test
    public void getDistance() throws NoSuchFieldException, IllegalAccessException {
         double expectedDistance = 123.4;
        Horse horse = new Horse("joe", 1, expectedDistance);
        assertEquals(expectedDistance, horse.getSpeed());
     }

     @Test
     public void zeroDistanceByDefault() {
         Horse horse = new Horse("joe", 1);
         assertEquals(0, horse.getDistance());
     }

     @Test
    public void moveUsesGetRandom() {
        try (MockedStatic<Horse> mockedStatic = mockStatic(Horse.class)) {
            new Horse("joe",1).move();

            mockedStatic.verify(() -> Horse.getRandomDouble(0.2, 0.9));
        }
     }

     @ParameterizedTest
     @ValueSource( doubles = {0.1, 0.3, 0.5, 0.7, 0.9, 1.0, Double.MAX_VALUE, 0.0})
    public void moveC(double random) {
        try ( MockedStatic<Horse> mockedStatic = mockStatic(Horse.class)) {
            Horse horse = new Horse("joe", 11, 234);
            mockedStatic.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(random);

            horse.move();

            assertEquals(11 + 234 + random, horse.getDistance());
        }
     }
}
