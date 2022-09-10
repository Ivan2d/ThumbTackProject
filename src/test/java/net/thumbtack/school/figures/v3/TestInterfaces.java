package net.thumbtack.school.figures.v3;

import net.thumbtack.school.colors.v3.Color;
import net.thumbtack.school.colors.v3.ColorException;
import net.thumbtack.school.iface.v3.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestInterfaces {

    @Test
    public void testMove() throws ColorException {
        ColoredRectangle coloredRectangle = new ColoredRectangle(0, 0, 10, 10, Color.RED);
        Movable movable = coloredRectangle;
        movable.moveRel(10, 10);
        assertAll(
                () -> assertEquals(5, coloredRectangle.getTopLeft().getX()),
                () -> assertEquals(5, coloredRectangle.getTopLeft().getY()),
                () -> assertEquals(15, coloredRectangle.getBottomRight().getX()),
                () -> assertEquals(15, coloredRectangle.getBottomRight().getY()));

        Resizable resizable = coloredRectangle;
        resizable.resize(2);
        assertAll(
                () -> assertEquals(0, coloredRectangle.getTopLeft().getX()),
                () -> assertEquals(0, coloredRectangle.getTopLeft().getY()),
                () -> assertEquals(20, coloredRectangle.getBottomRight().getX()),
                () -> assertEquals(20, coloredRectangle.getBottomRight().getY())
        );
        Stretchable stretchable = coloredRectangle;
        stretchable.stretch(2, 3);
        assertAll(
                () -> assertEquals(-10, coloredRectangle.getTopLeft().getX()),
                () -> assertEquals(-20, coloredRectangle.getTopLeft().getY()),
                () -> assertEquals(30, coloredRectangle.getBottomRight().getX()),
                () -> assertEquals(40, coloredRectangle.getBottomRight().getY())
        );
        HasArea hasArea = coloredRectangle;
        assertEquals(2400, hasArea.getArea());
        Colored colored = coloredRectangle;
        colored.setColor(Color.BLUE);
        assertEquals(Color.BLUE, colored.getColor());
    }

}
