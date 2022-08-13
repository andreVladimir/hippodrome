import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class HippodromeTest {
    @Test
    public void nullArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Hippodrome(null));
    }

    @Test
    public void nullArgumentExceptionMessage() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> new Hippodrome(null));
        assertEquals("Horses cannot be null.", thrown.getMessage());
    }

    @Test
    public void emptyListException() {
        List<Horse> list = new ArrayList<>();
        assertThrows(IllegalArgumentException.class, () -> new Hippodrome(list));
    }
    @Test
    public void emptyListExceptionMessage() {
        List<Horse> list = new ArrayList<>();
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> new Hippodrome(list));
        assertEquals("Horses cannot be empty.", thrown.getMessage());
    }

    @Test
//    @Disabled
    public void getHorsesOrder() {
        List<Horse> list = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            list.add(new Horse(Integer.valueOf(i).toString(), 1, 1));
        }
        Hippodrome hippodrome = new Hippodrome(list);
        List<Horse> hippodromeHorsesList = hippodrome.getHorses();
        assertEquals(list, hippodromeHorsesList);
    }

    @Test
    public void move() {
        List<Horse> horses = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            horses.add(mock(Horse.class));
        }
        Hippodrome hippodrome = new Hippodrome(horses);

        hippodrome.move();

        for(Horse horse : horses) {
            verify(horse).move();
        }
    }

    @Test
    public void getWinner() {
        Horse horse1 = new Horse("joe", 1, Double.MAX_VALUE);
        Horse horse2 = new Horse("joe", 1, 2);
        Horse horse3 = new Horse("joe", 1, 333);
        Horse horse4 = new Horse("joe", 1, 44444.111);
        Hippodrome hippodrome = new Hippodrome(List.of(horse1, horse2, horse3, horse4));

        assertSame(horse1, hippodrome.getWinner());


    }
}
