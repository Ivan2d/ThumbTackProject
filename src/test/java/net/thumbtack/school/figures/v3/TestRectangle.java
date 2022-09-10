package net.thumbtack.school.figures.v3;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestRectangle {

    private static final double DOUBLE_EPS = 1E-6;

    @Test
    public void testRectangle1() {
        Point center = new Point(20, 30);
        Rectangle rect = new Rectangle(center, 20, 20);
        assertAll(
                () -> assertEquals(10, rect.getTopLeft().getX()),
                () -> assertEquals(20, rect.getTopLeft().getY()),
                () -> assertEquals(30, rect.getBottomRight().getX()),
                () -> assertEquals(40, rect.getBottomRight().getY()),
                () -> assertEquals(20, rect.getWidth()),
                () -> assertEquals(20, rect.getHeight())
        );
    }

    @Test
    public void testRectangle2() {
        Rectangle rect = new Rectangle(20, 30, 20, 20);
        assertAll(
                () -> assertEquals(10, rect.getTopLeft().getX()),
                () -> assertEquals(20, rect.getTopLeft().getY()),
                () -> assertEquals(30, rect.getBottomRight().getX()),
                () -> assertEquals(40, rect.getBottomRight().getY()),
                () -> assertEquals(20, rect.getWidth(), 20),
                () -> assertEquals(20, rect.getHeight(), 20)
        );
    }

    @Test
    public void testRectangle3() {
        Rectangle rect = new Rectangle(10, 20);
        assertAll(
                () -> assertEquals(-5, rect.getTopLeft().getX()),
                () -> assertEquals(-10, rect.getTopLeft().getY()),
                () -> assertEquals(5, rect.getBottomRight().getX()),
                () -> assertEquals(10, rect.getBottomRight().getY()),
                () -> assertEquals(10, rect.getWidth()),
                () -> assertEquals(20, rect.getHeight())
        );
    }

    @Test
    public void testRectangle4() {
        Rectangle rect = new Rectangle();
        assertAll(
                () -> assertEquals(-1, rect.getTopLeft().getX()),
                () -> assertEquals(-1, rect.getTopLeft().getY()),
                () -> assertEquals(1, rect.getBottomRight().getX()),
                () -> assertEquals(1, rect.getBottomRight().getY()),
                () -> assertEquals(2, rect.getWidth()),
                () -> assertEquals(2, rect.getHeight())
        );
    }

    @Test
    public void testMoveRectangle() {
        Rectangle rect = new Rectangle(20, 30, 20, 20);
        rect.moveRel(100, 50);
        assertAll(
                () -> assertEquals(110, rect.getTopLeft().getX()),
                () -> assertEquals(70, rect.getTopLeft().getY()),
                () -> assertEquals(130, rect.getBottomRight().getX()),
                () -> assertEquals(90, rect.getBottomRight().getY())
        );
        rect.moveTo(100, 200);
        assertAll(
                () -> assertEquals(90, rect.getTopLeft().getX()),
                () -> assertEquals(190, rect.getTopLeft().getY()),
                () -> assertEquals(110, rect.getBottomRight().getX()),
                () -> assertEquals(210, rect.getBottomRight().getY())
        );
        rect.moveTo(new Point(1000, 2000));
        assertAll(
                () -> assertEquals(990, rect.getTopLeft().getX()),
                () -> assertEquals(1990, rect.getTopLeft().getY()),
                () -> assertEquals(1010, rect.getBottomRight().getX()),
                () -> assertEquals(2010, rect.getBottomRight().getY())
        );
    }

    @Test
    public void testResizeRectangle() {
        Rectangle rect = new Rectangle(20, 30, 20, 20);
        rect.resize(3);
        assertAll(
                () -> assertEquals(-10, rect.getTopLeft().getX()),
                () -> assertEquals(0, rect.getTopLeft().getY()),
                () -> assertEquals(50, rect.getBottomRight().getX()),
                () -> assertEquals(60, rect.getBottomRight().getY())
        );
    }

    @Test
    public void testStretchRectangle1() {
        Rectangle rect = new Rectangle(20, 30, 20, 20);
        rect.stretch(3, 5);
        assertAll(
                () -> assertEquals(-10, rect.getTopLeft().getX()),
                () -> assertEquals(-20, rect.getTopLeft().getY()),
                () -> assertEquals(50, rect.getBottomRight().getX()),
                () -> assertEquals(80, rect.getBottomRight().getY())
        );
    }


    @Test
    public void testIsPointInsideRectangle1() {
        Rectangle rect = new Rectangle(20, 30, 20, 20);
        assertAll(
                () -> assertTrue(rect.isInside(20, 30)),
                () -> assertTrue(rect.isInside(10, 30)),
                () -> assertTrue(rect.isInside(30, 30)),
                () -> assertTrue(rect.isInside(10, 40)),
                () -> assertFalse(rect.isInside(0, 0)),
                () -> assertFalse(rect.isInside(10, 100)),
                () -> assertFalse(rect.isInside(-10, 20)),
                () -> assertFalse(rect.isInside(10, -20))
        );
    }

    @Test
    public void testIsPointInsideRectangle2() {
        Rectangle rect = new Rectangle(20, 30, 20, 20);
        assertAll(
                () -> assertTrue(rect.isInside(new Point(20, 30))),
                () -> assertTrue(rect.isInside(new Point(10, 30))),
                () -> assertTrue(rect.isInside(new Point(30, 30))),
                () -> assertTrue(rect.isInside(new Point(10, 40))),
                () -> assertFalse(rect.isInside(new Point(0, 0))),
                () -> assertFalse(rect.isInside(new Point(10, 100))),
                () -> assertFalse(rect.isInside(new Point(-10, 20))),
                () -> assertFalse(rect.isInside(new Point(10, -20)))
        );
    }

    @Test
    public void testIsIntersectsRectangle() {
        Rectangle rect = new Rectangle(20, 30, 20, 20);
        assertAll(
                () -> assertTrue(rect.isIntersects(new Rectangle(20, 30, 10, 10))),
                () -> assertTrue(rect.isIntersects(new Rectangle(10, 30, 40, 20))),
                () -> assertTrue(rect.isIntersects(new Rectangle(30, 30, 20, 20))),
                () -> assertTrue(rect.isIntersects(new Rectangle(20, 30, 60, 20))),
                () -> assertTrue(rect.isIntersects(new Rectangle(20, 10, 20, 60))),
                () -> assertTrue(rect.isIntersects(new Rectangle(20, 40, 20, 40))),
                () -> assertTrue(rect.isIntersects(new Rectangle(30, 40, 60, 80))),
                () -> assertTrue(rect.isIntersects(new Rectangle(10, 10, 20, 20))),
                () -> assertTrue(rect.isIntersects(new Rectangle(30, 40, 20, 20))),
                () -> assertFalse(rect.isIntersects(new Rectangle(-35, 30, 10, 20))),
                () -> assertFalse(rect.isIntersects(new Rectangle(120, 130, 20, 20))),
                () -> assertFalse(rect.isIntersects(new Rectangle(20, 130, 20, 20))),
                () -> assertFalse(rect.isIntersects(new Rectangle(20, -30, 20, 20)))
        );
    }

    @Test
    public void testIsRectangleInsideRectangle() {
        Rectangle rect = new Rectangle(20, 30, 20, 20);
        assertAll(
                () -> assertTrue(rect.isInside(new Rectangle(20, 30, 10, 10))),
                () -> assertFalse(rect.isInside(new Rectangle(-35, 30, 10, 20))),
                () -> assertFalse(rect.isInside(new Rectangle(120, 130, 20, 20))),
                () -> assertFalse(rect.isInside(new Rectangle(20, 130, 20, 20))),
                () -> assertFalse(rect.isInside(new Rectangle(20, -30, 20, 20)))
        );
    }

    @Test
    public void testEqualsRectangle() {
        Rectangle rect1 = new Rectangle(10, 20, 30, 40);
        Rectangle rect2 = new Rectangle(10, 20, 30, 40);
        Rectangle rect3 = new Rectangle(20, 30, 40, 50);
        assertEquals(rect1, rect2);
        assertNotEquals(rect1, rect3);
    }

    @Test
    public void testAreaRectangle() {
        Rectangle rect1 = new Rectangle(10, 20, 30, 40);
        Rectangle rect2 = new Rectangle(20, 30, 40, 80);
        assertEquals(1200, rect1.getArea(), DOUBLE_EPS);
        assertEquals(3200, rect2.getArea(), DOUBLE_EPS);
    }

    @Test
    public void testPerimeterRectangle() {
        Rectangle rect1 = new Rectangle(10, 20, 30, 40);
        Rectangle rect2 = new Rectangle(20, 30, 40, 80);
        assertEquals(140, rect1.getPerimeter(), DOUBLE_EPS);
        assertEquals(240, rect2.getPerimeter(), DOUBLE_EPS);
    }
}
