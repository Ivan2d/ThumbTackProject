package net.thumbtack.school.figures.v3;
import net.thumbtack.school.colors.v3.Color;
import net.thumbtack.school.colors.v3.ColorException;
import net.thumbtack.school.iface.v3.Colored;

import java.util.Objects;

public class ColoredCircle extends Circle implements Colored {

    private Color color;

    public ColoredCircle(int xCenter, int yCenter, int radius, Color color) throws ColorException {//2
        super(xCenter, yCenter, radius);
        Color.colorEqualsNull(color);
        this.color = color;
    }

    public ColoredCircle(Point center, int radius, Color color) throws ColorException{//1
        this(center.getX(), center.getY(), radius, color);
    }

    public ColoredCircle(int radius, Color color) throws ColorException {//3
        this(0, 0, radius, color);
    }

    public ColoredCircle(Color color) throws ColorException {//4
        this(0, 0, 1, color);
    }

    public ColoredCircle(Point center, int radius, String color) throws ColorException {//1
        this(center.getX(), center.getY(), radius, color);
    }

    public ColoredCircle(int xCenter, int yCenter, int radius, String color) throws ColorException {//2
        this(xCenter, yCenter, radius, Color.colorFromString(color));
    }

    public ColoredCircle(int radius, String color) throws ColorException {//3
        this(0, 0, radius, color);
    }

    public ColoredCircle(String color) throws ColorException {//4
        this(0, 0, 1, color);
    }

    public ColoredCircle() throws ColorException {//5
        this(0, 0, 1, String.valueOf(Color.RED));
    }

    @Override
    public void setColor(String colorString) throws ColorException {
        setColor(Color.colorFromString(colorString));
        Color.colorEqualsNull(Color.colorFromString(colorString));
    }

    @Override
    public void setColor(Color color) throws ColorException {
        this.color = color;
        Color.colorEqualsNull(color);
    }


    public Color getColor(){//10
        return color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ColoredCircle that = (ColoredCircle) o;
        return color == that.color;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), color);
    }
}