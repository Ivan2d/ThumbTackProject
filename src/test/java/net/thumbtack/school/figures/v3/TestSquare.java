package net.thumbtack.school.figures.v3;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestSquare {

    private static final double DOUBLE_EPS = 1E-6;

    @Test
    public void testSquare1() {
        Point center = new Point(15, 25);
        Square square = new Square(center, 10);
        assertAll(
                () -> assertEquals(10, square.getTopLeft().getX()),
                () -> assertEquals(20, square.getTopLeft().getY()),
                () -> assertEquals(20, square.getBottomRight().getX()),
                () -> assertEquals(30, square.getBottomRight().getY()),
                () -> assertEquals(square.getLength(), 10))
        ;
    }

    @Test
    public void testSquare2() {
        Square square = new Square(15, 25, 10);
        assertAll(
                () -> assertEquals(10, square.getTopLeft().getX()),
                () -> assertEquals(20, square.getTopLeft().getY()),
                () -> assertEquals(20, square.getBottomRight().getX()),
                () -> assertEquals(30, square.getBottomRight().getY()),
                () -> assertEquals(10, square.getLength())
        );
    }

    @Test
    public void testSquare3() {
        Square square = new Square(10);
        assertAll(
                () -> assertEquals(-5, square.getTopLeft().getX()),
                () -> assertEquals(-5, square.getTopLeft().getY()),
                () -> assertEquals(5, square.getBottomRight().getX()),
                () -> assertEquals(5, square.getBottomRight().getY()),
                () -> assertEquals(10, square.getLength())
        );
    }

    @Test
    public void testSquare4() {
        Square square = new Square();
        assertAll(
                () -> assertEquals(-1, square.getTopLeft().getX()),
                () -> assertEquals(-1, square.getTopLeft().getY()),
                () -> assertEquals(1, square.getBottomRight().getX()),
                () -> assertEquals(1, square.getBottomRight().getY()),
                () -> assertEquals(2, square.getLength())
        );
    }

    @Test
    public void testMoveSquare() {
        Square square = new Square(25, 35, 30);
        square.moveRel(100, 50);
        assertAll(
                () -> assertEquals(110, square.getTopLeft().getX()),
                () -> assertEquals(70, square.getTopLeft().getY()),
                () -> assertEquals(140, square.getBottomRight().getX()),
                () -> assertEquals(100, square.getBottomRight().getY()),
                () -> assertEquals(30, square.getLength())
        );
        square.moveTo(100, 200);
        assertAll(
                () -> assertEquals(85, square.getTopLeft().getX()),
                () -> assertEquals(185, square.getTopLeft().getY()),
                () -> assertEquals(115, square.getBottomRight().getX()),
                () -> assertEquals(215, square.getBottomRight().getY()),
                () -> assertEquals(30, square.getLength())
        );
        square.moveTo(new Point(1000, 2000));
        assertAll(
                () -> assertEquals(985, square.getTopLeft().getX()),
                () -> assertEquals(1985, square.getTopLeft().getY()),
                () -> assertEquals(1015, square.getBottomRight().getX()),
                () -> assertEquals(2015, square.getBottomRight().getY()),
                () -> assertEquals(30, square.getLength())
        );
    }

    @Test
    public void testResizeSquare() {
        Square square = new Square(25, 35, 30);
        square.resize(3);
        assertAll(
                () -> assertEquals(-20, square.getTopLeft().getX()),
                () -> assertEquals(-10, square.getTopLeft().getY()),
                () -> assertEquals(70, square.getBottomRight().getX()),
                () -> assertEquals(80, square.getBottomRight().getY()),
                () -> assertEquals(90, square.getLength())
        );
    }

    @Test
    public void testAreaSquare() {
        Square square1 = new Square(10, 20, 30);
        Square square2 = new Square(20, 30, 40);
        assertEquals(900, square1.getArea(), DOUBLE_EPS);
        assertEquals(1600, square2.getArea(), DOUBLE_EPS);
    }

    @Test
    public void testPerimeterSquare() {
        Square square1 = new Square(10, 20, 30);
        Square square2 = new Square(20, 30, 40);
        assertEquals(120, square1.getPerimeter(), DOUBLE_EPS);
        assertEquals(160, square2.getPerimeter(), DOUBLE_EPS);
    }

    @Test
    public void testIsPointInsideSquare1() {
        Square square = new Square(25, 35, 30);
        assertAll(
                () -> assertTrue(square.isInside(20, 30)),
                () -> assertTrue(square.isInside(10, 30)),
                () -> assertTrue(square.isInside(30, 30)),
                () -> assertTrue(square.isInside(10, 40)),
                () -> assertFalse(square.isInside(10, 60)),
                () -> assertFalse(square.isInside(0, 0)),
                () -> assertFalse(square.isInside(10, 100)),
                () -> assertFalse(square.isInside(-10, 20)),
                () -> assertFalse(square.isInside(10, -20))
        );
    }

    @Test
    public void testIsPointInsideSquare2() {
        Square square = new Square(25, 35, 30);
        assertAll(
                () -> assertTrue(square.isInside(new Point(20, 30))),
                () -> assertTrue(square.isInside(new Point(10, 30))),
                () -> assertTrue(square.isInside(new Point(30, 30))),
                () -> assertTrue(square.isInside(new Point(10, 40))),
                () -> assertFalse(square.isInside(10, 60)),
                () -> assertFalse(square.isInside(new Point(0, 0))),
                () -> assertFalse(square.isInside(new Point(10, 100))),
                () -> assertFalse(square.isInside(new Point(-10, 20))),
                () -> assertFalse(square.isInside(new Point(10, -20)))
        );
    }

    @Test
    public void testIsIntersectsSquare() {
        Square square = new Square(25, 35, 30);
        assertAll(
                () -> assertTrue(square.isIntersects(new Square(30, 30, 20))),
                () -> assertTrue(square.isIntersects(new Square(30, 10, 20))),
                () -> assertTrue(square.isIntersects(new Square(10, 30, 20))),
                () -> assertTrue(square.isIntersects(new Square(15, 45, 50))),
                () -> assertTrue(square.isIntersects(new Square(15, 5, 30))),
                () -> assertTrue(square.isIntersects(new Square(25, 35, 30))),
                () -> assertTrue(square.isIntersects(new Square(15, 5, 50))),
                () -> assertTrue(square.isIntersects(new Square(10, 20, 20))),
                () -> assertTrue(square.isIntersects(new Square(40, 50, 40))),
                () -> assertFalse(square.isIntersects(new Square(-25, 35, 30))),
                () -> assertFalse(square.isIntersects(new Square(175, 185, 130))),
                () -> assertFalse(square.isIntersects(new Square(25, 135, 30))),
                () -> assertFalse(square.isIntersects(new Square(25, -25, 30)))
        );
    }

    @Test
    public void testIsSquareInsideSquare() {
        Square square = new Square(25, 35, 30);
        assertAll(
                () -> assertTrue(square.isInside(new Square(25, 35, 20))),
                () -> assertFalse(square.isInside(new Square(-25, 35, 30))),
                () -> assertFalse(square.isInside(new Square(175, 185, 130))),
                () -> assertFalse(square.isInside(new Square(25, 135, 30))),
                () -> assertFalse(square.isInside(new Square(25, -25, 30)))
        );
    }

    @Test
    public void testEqualsSquare() {
        Square square1 = new Square(new Point(10, 20), 30);
        Square square2 = new Square(10, 20, 30);
        Square square3 = new Square(20, 30, 40);
        assertEquals(square1, square2);
        assertNotEquals(square1, square3);
    }

}
