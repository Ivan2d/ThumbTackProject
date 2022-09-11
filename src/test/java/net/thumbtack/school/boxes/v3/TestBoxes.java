package net.thumbtack.school.boxes.v3;

import net.thumbtack.school.colors.v3.Color;
import net.thumbtack.school.colors.v3.ColorException;
import net.thumbtack.school.figures.v3.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestBoxes {
    private static final double DOUBLE_EPS = 1E-6;

    @Test
    public void testRectangleBox() {
        Point center = new Point(20, 30);
        Rectangle rect = new Rectangle(center, 20, 20);
        Box<Rectangle> rectBox = new Box<>(rect);
        assertAll(
                () -> assertEquals(10, rectBox.getContent().getTopLeft().getX()),
                () -> assertEquals(20, rectBox.getContent().getTopLeft().getY()),
                () -> assertEquals(30, rectBox.getContent().getBottomRight().getX()),
                () -> assertEquals(40, rectBox.getContent().getBottomRight().getY()),
                () -> assertEquals(20, rectBox.getContent().getWidth()),
                () -> assertEquals(20, rectBox.getContent().getHeight())
        );
    }

    @Test
    public void testEllipseBox() {
        Point center = new Point(10, 20);
        Ellipse ellipse = new Ellipse(center, 10, 20);
        Box<Ellipse> ellipseBox = new Box<>(ellipse);
        assertAll(
                () -> assertEquals(10, ellipseBox.getContent().getCenter().getX()),
                () -> assertEquals(20, ellipseBox.getContent().getCenter().getY()),
                () -> assertEquals(10, ellipseBox.getContent().getXAxis()),
                () -> assertEquals(20, ellipseBox.getContent().getYAxis()),
                () -> assertEquals(Math.PI * 10 * 20 / 4, ellipseBox.getArea(), DOUBLE_EPS)
        );
    }

    @Test
    public void testCircleBox() {
        Point center = new Point(10, 20);
        Circle circle = new Circle(center, 10);
        Box<Circle> circleBox = new Box<>(circle);
        assertAll(
                () -> assertEquals(10, circleBox.getContent().getCenter().getX()),
                () -> assertEquals(20, circleBox.getContent().getCenter().getY()),
                () -> assertEquals(10, circleBox.getContent().getRadius()),
                () -> assertEquals(Math.PI * 10 * 10, circleBox.getArea(), DOUBLE_EPS)
        );
    }

    @Test
    public void testSquareBox() {
        Point center = new Point(15, 25);
        Square square = new Square(center, 10);
        Box<Square> squareBox = new Box<>(square);
        assertAll(
                () -> assertEquals(10, squareBox.getContent().getTopLeft().getX()),
                () -> assertEquals(20, squareBox.getContent().getTopLeft().getY()),
                () -> assertEquals(20, squareBox.getContent().getBottomRight().getX()),
                () -> assertEquals(30, squareBox.getContent().getBottomRight().getY()),
                () -> assertEquals(squareBox.getContent().getLength(), 10))
        ;
    }

    @Test
    public void testColoredRectangleBox() throws ColorException {
        Point center = new Point(20, 30);
        ColoredRectangle coloredRect = new ColoredRectangle(center, 20, 20, Color.RED);
        Box<ColoredRectangle> rectBox = new Box<>(coloredRect);
        assertAll(
                () -> assertEquals(10, rectBox.getContent().getTopLeft().getX()),
                () -> assertEquals(20, rectBox.getContent().getTopLeft().getY()),
                () -> assertEquals(30, rectBox.getContent().getBottomRight().getX()),
                () -> assertEquals(40, rectBox.getContent().getBottomRight().getY()),
                () -> assertEquals(20, rectBox.getContent().getWidth()),
                () -> assertEquals(20, rectBox.getContent().getHeight()),
                () -> assertEquals(Color.RED, rectBox.getContent().getColor())
        );
    }

    @Test
    public void testColoredCircleBox() throws ColorException {
        Point center = new Point(10, 20);
        ColoredCircle coloredCircle = new ColoredCircle(center, 10, Color.GREEN);
        Box<ColoredCircle> coloredCircleBox = new Box<>(coloredCircle);
        assertAll(
                () -> assertEquals(10, coloredCircleBox.getContent().getCenter().getX()),
                () -> assertEquals(20, coloredCircleBox.getContent().getCenter().getY()),
                () -> assertEquals(10, coloredCircleBox.getContent().getRadius()),
                () -> assertEquals(Color.GREEN, coloredCircle.getColor())
        );
    }

    @Test
    public void testAreaBox() {
        Point center = new Point(20, 30);
        Rectangle rect = new Rectangle(center, 20, 20);
        Box<Rectangle> rectBox = new Box<>(rect);
        assertEquals(400, rectBox.getContent().getArea(), DOUBLE_EPS);
    }

    @Test
    public void testAreaEqualBox1() throws ColorException {
        Rectangle rect = new Rectangle(0, 0, 20, 60);
        Box<Rectangle> rectBox = new Box<>(rect);
        ColoredRectangle coloredRectangle = new ColoredRectangle(0, 0, 30, 40, Color.BLUE);
        Box<ColoredRectangle> coloredRectangleBox = new Box<>(coloredRectangle);
        assertTrue(coloredRectangleBox.isAreaEqual(rectBox));
    }

    @Test
    public void testAreaEqualBox2() {
        Rectangle rect = new Rectangle(0, 0, 20, 80);
        Box<Rectangle> rectBox = new Box<>(rect);
        Square square = new Square(0, 0, 40);
        Box<Square> squareBox = new Box<>(square);
        assertTrue(Box.isAreaEqual(rectBox, squareBox));
    }

    @Test
    public void testRectangleArrayBox() {
        Rectangle rect1 = new Rectangle(10, 20, 30, 40);
        Rectangle rect2 = new Rectangle(10, 20, 40, 30);
        Rectangle[] rectangles1 = new Rectangle[]{rect1, rect2};
        ArrayBox<Rectangle> rectArrayBox1 = new ArrayBox<>(rectangles1);
        Rectangle[] rectangles2 = new Rectangle[]{rect2, rect1};
        ArrayBox<Rectangle> rectArrayBox2 = new ArrayBox<>(rectangles2);
        assertTrue(rectArrayBox1.isSameSize(rectArrayBox2));
    }

    @Test
    public void testRectangleEllipseArrayBoxes() {
        Rectangle rect1 = new Rectangle(10, 20, 30, 40);
        Rectangle rect2 = new Rectangle(10, 20, 30, 40);
        Rectangle[] rectangles = new Rectangle[]{rect1, rect2};
        ArrayBox<Rectangle> rectArrayBox = new ArrayBox<>(rectangles);
        Point center = new Point(10, 20);
        Ellipse ellipse = new Ellipse(center, 10, 20);
        Ellipse[] ellipses = new Ellipse[]{ellipse};
        ArrayBox<Ellipse> ellipseArrayBox = new ArrayBox<>(ellipses);
        assertFalse(rectArrayBox.isSameSize(ellipseArrayBox));
    }

    @Test
    public void testMixedFiguresArrayBoxes() {
        Rectangle rect1 = new Rectangle(10, 20, 30, 40);
        Point center = new Point(10, 20);
        Ellipse ellipse = new Ellipse(center, 10, 20);
        Figure[] figures1 = new Figure[]{rect1, ellipse};
        ArrayBox<Figure> figureArrayBox1 = new ArrayBox<>(figures1);
        Rectangle rect2 = new Rectangle(10, 20, 30, 40);
        Figure[] figures2 = new Figure[]{rect2};
        ArrayBox<Figure> figureArrayBox2 = new ArrayBox<>(figures2);
        assertFalse(figureArrayBox1.isSameSize(figureArrayBox2));
    }

    @Test
    public void testPairBox1() {
        Rectangle rect = new Rectangle(10, 20, 30, 40);
        Point center = new Point(10, 20);
        Circle circle = new Circle(center, 10);
        PairBox<Rectangle, Circle> pairBox1 = new PairBox<>(rect, circle);
        PairBox<Circle, Rectangle> pairBox2 = new PairBox<>(circle, rect);
        assertTrue(pairBox1.isAreaEqual(pairBox2));
        assertTrue(PairBox.isAreaEqual(pairBox1, pairBox2));
    }

    @Test
    public void testPairBox2() throws ColorException {
        Rectangle rect = new Rectangle(10, 20, 30, 40);
        ColoredRectangle coloredRectangle = new ColoredRectangle(0, 0, 30, 40, Color.BLUE);
        PairBox<Rectangle, ColoredRectangle> pairBox1 = new PairBox<>(rect, coloredRectangle);
        PairBox<ColoredRectangle, Rectangle> pairBox2 = new PairBox<>(coloredRectangle, rect);
        assertTrue(pairBox1.isAreaEqual(pairBox2));
        assertTrue(PairBox.isAreaEqual(pairBox1, pairBox2));
    }

    @Test
    public void testRectangleNamedBox() {
        Point center = new Point(20, 30);
        Rectangle rect = new Rectangle(center, 20, 20);
        NamedBox<Rectangle> rectBox = new NamedBox<>(rect, "Very good rect");
        assertAll(
                () -> assertEquals(10, rectBox.getContent().getTopLeft().getX()),
                () -> assertEquals(20, rectBox.getContent().getTopLeft().getY()),
                () -> assertEquals(30, rectBox.getContent().getBottomRight().getX()),
                () -> assertEquals(40, rectBox.getContent().getBottomRight().getY()),
                () -> assertEquals(20, rectBox.getContent().getWidth()),
                () -> assertEquals(20, rectBox.getContent().getHeight()),
                () -> assertEquals(400, rectBox.getArea(), DOUBLE_EPS),
                () -> assertEquals("Very good rect", rectBox.getName())
        );
    }

    @Test
    public void testNamedBoxArea() {
        Point center = new Point(20, 30);
        Rectangle rect = new Rectangle(center, 20, 20);
        NamedBox<Rectangle> rectBox = new NamedBox<>(rect, "Very good rect");
        assertEquals(400, rectBox.getArea(), DOUBLE_EPS);
        assertEquals("Very good rect", rectBox.getName());
    }

    /*
	@Test
	public void testMustNotBeCompiled() {
		Box<String> stringBox = new Box<>("My String");
	}
    */


}
