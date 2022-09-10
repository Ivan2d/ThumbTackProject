package net.thumbtack.school.figures.misc.v3;

import net.thumbtack.school.misc.v2.Pen;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class TestPen {

    @Test
    public void testPen() {
        Pen apple = new Pen(1);
        assertEquals(1, apple.getColor());
    }

    @Test
    public void setPen() {
        Pen apple = new Pen();
        assertEquals(2, apple.getColor());
        apple.setColor(3);
        assertNotEquals(1, apple.getColor());
    }

    @Test
    public void testEqualsPen() {
        Pen apple1 = new Pen(1);
        Pen apple2 = new Pen(1);
        Pen apple3 = new Pen(3);
        assertEquals(apple1, apple2);
        assertNotEquals(apple1, apple3);
    }
}