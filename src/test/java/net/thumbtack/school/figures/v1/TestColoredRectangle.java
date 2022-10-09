package net.thumbtack.school.figures.v1;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestColoredRectangle {

    private static final double DOUBLE_EPS = 1E-6;

    @Test
    public void testColoredRectangle1() {
        Point center = new Point(20, 30);
        ColoredRectangle coloredRect = new ColoredRectangle(center, 20, 20, 1);
        assertAll(
                () -> assertEquals(10, coloredRect.getTopLeft().getX()),
                () -> assertEquals(20, coloredRect.getTopLeft().getY()),
                () -> assertEquals(30, coloredRect.getBottomRight().getX()),
                () -> assertEquals(40, coloredRect.getBottomRight().getY()),
                () -> assertEquals(20, coloredRect.getWidth()),
                () -> assertEquals(20, coloredRect.getHeight())
        );
    }

    @Test
    public void testColoredRectangle2() {
        ColoredRectangle coloredRect = new ColoredRectangle(20, 30, 20, 20, 1);
        assertAll(
                () -> assertEquals(10, coloredRect.getTopLeft().getX()),
                () -> assertEquals(20, coloredRect.getTopLeft().getY()),
                () -> assertEquals(30, coloredRect.getBottomRight().getX()),
                () -> assertEquals(40, coloredRect.getBottomRight().getY()),
                () -> assertEquals(20, coloredRect.getWidth(), 20),
                () -> assertEquals(20, coloredRect.getHeight(), 20)
        );
    }

    @Test
    public void testColoredRectangle3() {
        ColoredRectangle coloredRect = new ColoredRectangle(10, 20, 1);
        assertAll(
                () -> assertEquals(-5, coloredRect.getTopLeft().getX()),
                () -> assertEquals(-10, coloredRect.getTopLeft().getY()),
                () -> assertEquals(5, coloredRect.getBottomRight().getX()),
                () -> assertEquals(10, coloredRect.getBottomRight().getY()),
                () -> assertEquals(10, coloredRect.getWidth()),
                () -> assertEquals(20, coloredRect.getHeight())
        );
    }

    @Test
    public void testColoredRectangle4() {
        ColoredRectangle coloredRect = new ColoredRectangle(1);
        assertAll(
                () -> assertEquals(-1, coloredRect.getTopLeft().getX()),
                () -> assertEquals(-1, coloredRect.getTopLeft().getY()),
                () -> assertEquals(1, coloredRect.getBottomRight().getX()),
                () -> assertEquals(1, coloredRect.getBottomRight().getY()),
                () -> assertEquals(2, coloredRect.getWidth()),
                () -> assertEquals(2, coloredRect.getHeight())
        );
    }

    @Test
    public void testColoredRectangle5() {
        ColoredRectangle coloredRect = new ColoredRectangle();
        assertAll(
                () -> assertEquals(-1, coloredRect.getTopLeft().getX()),
                () -> assertEquals(-1, coloredRect.getTopLeft().getY()),
                () -> assertEquals(1, coloredRect.getBottomRight().getX()),
                () -> assertEquals(1, coloredRect.getBottomRight().getY()),
                () -> assertEquals(2, coloredRect.getWidth()),
                () -> assertEquals(2, coloredRect.getHeight())
        );
    }

    @Test
    public void testSetColor() {
        ColoredRectangle coloredRect = new ColoredRectangle(1);
        assertAll(
                () -> assertEquals(-1, coloredRect.getTopLeft().getX()),
                () -> assertEquals(-1, coloredRect.getTopLeft().getY()),
                () -> assertEquals(1, coloredRect.getBottomRight().getX()),
                () -> assertEquals(1, coloredRect.getBottomRight().getY()),
                () -> assertEquals(2, coloredRect.getWidth()),
                () -> assertEquals(2, coloredRect.getHeight())
        );
        coloredRect.setColor(2);
        assertEquals(2, coloredRect.getColor());
    }

    @Test
    public void testMoveColoredRectangle() {
        ColoredRectangle coloredRect = new ColoredRectangle(20, 30, 20, 20, 1);
        coloredRect.moveRel(100, 50);
        assertAll(
                () -> assertEquals(110, coloredRect.getTopLeft().getX()),
                () -> assertEquals(70, coloredRect.getTopLeft().getY()),
                () -> assertEquals(130, coloredRect.getBottomRight().getX()),
                () -> assertEquals(90, coloredRect.getBottomRight().getY())
        );
        coloredRect.moveTo(100, 200);
        assertAll(
                () -> assertEquals(90, coloredRect.getTopLeft().getX()),
                () -> assertEquals(190, coloredRect.getTopLeft().getY()),
                () -> assertEquals(110, coloredRect.getBottomRight().getX()),
                () -> assertEquals(210, coloredRect.getBottomRight().getY())
        );
        coloredRect.moveTo(new Point(1000, 2000));
        assertAll(
                () -> assertEquals(990, coloredRect.getTopLeft().getX()),
                () -> assertEquals(1990, coloredRect.getTopLeft().getY()),
                () -> assertEquals(1010, coloredRect.getBottomRight().getX()),
                () -> assertEquals(2010, coloredRect.getBottomRight().getY())
        );
    }

    @Test
    public void testResizeColoredRectangle() {
        ColoredRectangle coloredRect = new ColoredRectangle(20, 30, 20, 20, 1);
        coloredRect.resize(3);
        assertAll(
                () -> assertEquals(-10, coloredRect.getTopLeft().getX()),
                () -> assertEquals(0, coloredRect.getTopLeft().getY()),
                () -> assertEquals(50, coloredRect.getBottomRight().getX()),
                () -> assertEquals(60, coloredRect.getBottomRight().getY())
        );
    }

    @Test
    public void testStretchColoredRectangle1() {
        ColoredRectangle coloredRect = new ColoredRectangle(20, 30, 20, 20, 1);
        coloredRect.stretch(3, 5);
        assertAll(
                () -> assertEquals(-10, coloredRect.getTopLeft().getX()),
                () -> assertEquals(-20, coloredRect.getTopLeft().getY()),
                () -> assertEquals(50, coloredRect.getBottomRight().getX()),
                () -> assertEquals(80, coloredRect.getBottomRight().getY())
        );
    }


    @Test
    public void testIsPointInsideColoredRectangle1() {
        ColoredRectangle coloredRect = new ColoredRectangle(20, 30, 20, 20, 1);
        assertAll(
                () -> assertTrue(coloredRect.isInside(20, 30)),
                () -> assertTrue(coloredRect.isInside(10, 30)),
                () -> assertTrue(coloredRect.isInside(30, 30)),
                () -> assertTrue(coloredRect.isInside(10, 40)),
                () -> assertFalse(coloredRect.isInside(0, 0)),
                () -> assertFalse(coloredRect.isInside(10, 100)),
                () -> assertFalse(coloredRect.isInside(-10, 20)),
                () -> assertFalse(coloredRect.isInside(10, -20))
        );
    }

    @Test
    public void testIsPointInsideColoredRectangle2() {
        ColoredRectangle coloredRect = new ColoredRectangle(20, 30, 20, 20, 1);
        assertAll(
                () -> assertTrue(coloredRect.isInside(new Point(20, 30))),
                () -> assertTrue(coloredRect.isInside(new Point(10, 30))),
                () -> assertTrue(coloredRect.isInside(new Point(30, 30))),
                () -> assertTrue(coloredRect.isInside(new Point(10, 40))),
                () -> assertFalse(coloredRect.isInside(new Point(0, 0))),
                () -> assertFalse(coloredRect.isInside(new Point(10, 100))),
                () -> assertFalse(coloredRect.isInside(new Point(-10, 20))),
                () -> assertFalse(coloredRect.isInside(new Point(10, -20)))
        );
    }

    @Test
    public void testIsIntersectsColoredRectangle() {
        ColoredRectangle coloredRect = new ColoredRectangle(20, 30, 20, 20, 1);
        assertAll(
                () -> assertTrue(coloredRect.isIntersects(new ColoredRectangle(20, 30, 10, 10, 1))),
                () -> assertTrue(coloredRect.isIntersects(new ColoredRectangle(10, 30, 40, 20, 1))),
                () -> assertTrue(coloredRect.isIntersects(new ColoredRectangle(30, 30, 20, 20, 1))),
                () -> assertTrue(coloredRect.isIntersects(new ColoredRectangle(20, 30, 60, 20, 1))),
                () -> assertTrue(coloredRect.isIntersects(new ColoredRectangle(20, 10, 20, 60, 1))),
                () -> assertTrue(coloredRect.isIntersects(new ColoredRectangle(20, 40, 20, 40, 1))),
                () -> assertTrue(coloredRect.isIntersects(new ColoredRectangle(30, 40, 60, 80, 1))),
                () -> assertTrue(coloredRect.isIntersects(new ColoredRectangle(10, 10, 20, 20, 1))),
                () -> assertTrue(coloredRect.isIntersects(new ColoredRectangle(30, 40, 20, 20, 1))),
                () -> assertFalse(coloredRect.isIntersects(new ColoredRectangle(-35, 30, 10, 20, 1))),
                () -> assertFalse(coloredRect.isIntersects(new ColoredRectangle(120, 130, 20, 20, 1))),
                () -> assertFalse(coloredRect.isIntersects(new ColoredRectangle(20, 130, 20, 20, 1))),
                () -> assertFalse(coloredRect.isIntersects(new ColoredRectangle(20, -30, 20, 20, 1)))
        );
    }

    @Test
    public void testIsColoredRectangleInsideColoredRectangle() {
        ColoredRectangle coloredRect = new ColoredRectangle(20, 30, 20, 20, 1);
        assertAll(
                () -> assertTrue(coloredRect.isInside(new ColoredRectangle(20, 30, 10, 10, 1))),
                () -> assertFalse(coloredRect.isInside(new ColoredRectangle(-35, 30, 10, 20, 1))),
                () -> assertFalse(coloredRect.isInside(new ColoredRectangle(120, 130, 20, 20, 1))),
                () -> assertFalse(coloredRect.isInside(new ColoredRectangle(20, 130, 20, 20, 1))),
                () -> assertFalse(coloredRect.isInside(new ColoredRectangle(20, -30, 20, 20, 1)))
        );
    }

    @Test
    public void testEqualsColoredRectangle() {
        ColoredRectangle coloredRect1 = new ColoredRectangle(10, 20, 30, 40, 1);
        ColoredRectangle coloredRect2 = new ColoredRectangle(10, 20, 30, 40, 1);
        ColoredRectangle coloredRect3 = new ColoredRectangle(20, 30, 40, 50, 1);
        assertEquals(coloredRect1, coloredRect2);
        assertNotEquals(coloredRect1, coloredRect3);
    }

    @Test
    public void testAreaColoredRectangle() {
        ColoredRectangle coloredRect1 = new ColoredRectangle(10, 20, 30, 40, 1);
        ColoredRectangle coloredRect2 = new ColoredRectangle(20, 30, 40, 80, 1);
        assertEquals(1200, coloredRect1.getArea(), DOUBLE_EPS);
        assertEquals(3200, coloredRect2.getArea(), DOUBLE_EPS);
    }

    @Test
    public void testPerimeterColoredRectangle() {
        ColoredRectangle coloredRect1 = new ColoredRectangle(10, 20, 30, 40, 1);
        ColoredRectangle coloredRect2 = new ColoredRectangle(20, 30, 40, 80, 1);
        assertEquals(140, coloredRect1.getPerimeter(), DOUBLE_EPS);
        assertEquals(240, coloredRect2.getPerimeter(), DOUBLE_EPS);
    }
}
