package net.thumbtack.school.figures.v2;

import net.thumbtack.school.iface.v2.*;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.*;


public class TestTypes {

    @Test
    public void testTypes() throws NoSuchMethodException {

        assertTrue(Colored.class.isInterface());
        assertTrue(Stretchable.class.isInterface());
        assertTrue(Resizable.class.isInterface());
        assertTrue(HasArea.class.isInterface());
        assertTrue(Movable.class.isInterface());

        assertTrue(HasArea.class.isAssignableFrom(Figure.class));
        assertTrue(Resizable.class.isAssignableFrom(Stretchable.class));
        assertFalse(Colored.class.isAssignableFrom(Figure.class));
        assertTrue(Colored.class.isAssignableFrom(ColoredCircle.class));
        assertTrue(Colored.class.isAssignableFrom(ColoredRectangle.class));
        assertTrue(Resizable.class.isAssignableFrom(Figure.class));
        assertTrue(Movable.class.isAssignableFrom(Figure.class));
        assertFalse(Stretchable.class.isAssignableFrom(Figure.class));
        assertTrue(Figure.class.isAssignableFrom(Rectangle.class));
        assertTrue(Figure.class.isAssignableFrom(Square.class));
        assertTrue(Figure.class.isAssignableFrom(Circle.class));
        assertTrue(Figure.class.isAssignableFrom(Ellipse.class));
        assertFalse(Movable.class.isAssignableFrom(Resizable.class));
        assertTrue(Movable.class.isAssignableFrom(Point.class));
        assertFalse(Colored.class.isAssignableFrom(Point.class));
        assertFalse(HasArea.class.isAssignableFrom(Point.class));

        assertNotEquals(0, Figure.class.getModifiers() & Modifier.ABSTRACT);
        assertThrows(NoSuchMethodException.class, () -> Figure.class.getDeclaredMethod("moveTo", Point.class));
        assertEquals(0, Figure.class.getMethod("isInside", Point.class).getModifiers() & Modifier.ABSTRACT);
        assertEquals(0, Figure.class.getMethod("getCenter").getModifiers() & Modifier.ABSTRACT);
        assertEquals(0, Movable.class.getMethod("moveTo", Point.class).getModifiers() & Modifier.ABSTRACT);
        assertEquals(0, Figure.class.getMethod("moveTo", int.class, int.class).getModifiers() & Modifier.ABSTRACT);
        assertEquals(0, Figure.class.getMethod("moveRel", int.class, int.class).getModifiers() & Modifier.ABSTRACT);


    }
}
