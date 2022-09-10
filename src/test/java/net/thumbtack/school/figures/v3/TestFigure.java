package net.thumbtack.school.figures.v3;

import net.thumbtack.school.colors.v3.Color;
import net.thumbtack.school.colors.v3.ColorException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestFigure {

    private static final double DOUBLE_EPS = 1E-6;

    @Test
    public void testIsInside1() throws ColorException {
        Figure[] figures = new Figure[3];
        figures[0] = new Circle(0, 0, 10);
        figures[1] = new Rectangle(0, 0, 20, 20);
        figures[2] = new ColoredCircle(1, 1, 100, Color.RED);
        assertAll(
                () -> figures[0].isInside(0, 0),
                () -> figures[1].isInside(0, 0),
                () -> figures[2].isInside(0, 0))
        ;
    }

    @Test
    public void testIsInside2() throws ColorException {
        Figure[] figures = new Figure[3];
        figures[0] = new Circle(0, 0, 10);
        figures[1] = new Rectangle(0, 0, 20, 20);
        figures[2] = new ColoredCircle(1, 1, 100, Color.RED);
        assertAll(
                () -> figures[0].isInside(0, 0),
                () -> figures[1].isInside(0, 0),
                () -> figures[2].isInside(0, 0))
        ;
    }

    @Test
    public void testMove() {
        Rectangle rect = new Rectangle(0, 0, 10, 10);
        Figure figure = rect;
        figure.moveRel(10, 10);
        assertAll(
                () -> assertEquals(5, rect.getTopLeft().getX()),
                () -> assertEquals(5, rect.getTopLeft().getY()),
                () -> assertEquals(15, rect.getBottomRight().getX()),
                () -> assertEquals(15, rect.getBottomRight().getY()));
        figure.moveTo(100, 100);
        assertAll(
                () -> assertEquals(95, rect.getTopLeft().getX()),
                () -> assertEquals(95, rect.getTopLeft().getY()),
                () -> assertEquals(105, rect.getBottomRight().getX()),
                () -> assertEquals(105, rect.getBottomRight().getY())
        );
        figure.moveTo(new Point(1000, 1000));
        assertAll(
                () -> assertEquals(995, rect.getTopLeft().getX()),
                () -> assertEquals(995, rect.getTopLeft().getY()),
                () -> assertEquals(1005, rect.getBottomRight().getX()),
                () -> assertEquals(1005, rect.getBottomRight().getY())
        );

    }

    @Test
    public void testAreaAndPerimeter() throws ColorException {
        Figure figure = new Rectangle(0, 0, 10, 10);
        assertEquals(100, figure.getArea(), DOUBLE_EPS);
        assertEquals(40, figure.getPerimeter(), DOUBLE_EPS);
        figure = new ColoredCircle(1, 1, 100, Color.RED);
        assertEquals(Math.PI * 100 * 100, figure.getArea(), DOUBLE_EPS);
        assertEquals(2 * Math.PI * 100, figure.getPerimeter(), DOUBLE_EPS);
    }

}
