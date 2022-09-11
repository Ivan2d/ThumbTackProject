package net.thumbtack.school.figures.v3;
import net.thumbtack.school.colors.v3.Color;
import net.thumbtack.school.colors.v3.ColorException;
import net.thumbtack.school.iface.v3.Colored;
import net.thumbtack.school.iface.v3.HasArea;
import net.thumbtack.school.iface.v3.Stretchable;

import java.util.Objects;

public class ColoredRectangle extends Rectangle implements Colored, Stretchable, HasArea
{
    private Color color;

    // REVU В классе должен быть только один конструктор, явно присваивающий значения полям. Остальные должны вызывать другой конструктор
    public ColoredRectangle(Point center, int width, int height, Color color) throws ColorException {
        super(center, width, height);
        this.color = color;
    }

    public ColoredRectangle(int xCenter, int yCenter, int width, int height, Color color) throws ColorException {
        this(new Point(xCenter, yCenter), width, height, color);
    }

    public ColoredRectangle(int width, int height, Color color) throws ColorException {
        this(new Point(0,0), width, height, color);
    }

    public ColoredRectangle(Color color) throws ColorException {
        this(new Point(0,0), 2, 2, color);
        Color.colorEqualsNull(color);
    }

    public ColoredRectangle(Point center, int width, int height, String color) throws ColorException {
        super(center, width, height);
        this.color = Color.colorFromString(color);
    }

    public ColoredRectangle(int xCenter, int yCenter, int width, int height, String color) throws ColorException {
        this(new Point(xCenter, yCenter), width, height, color);
    }

    public ColoredRectangle(int width, int height, String color) throws ColorException {
        this(new Point(0,0), width, height, color);
    }

    public ColoredRectangle(String color) throws ColorException {
        this(new Point(0,0), 2, 2, color);
    }

    public ColoredRectangle() throws ColorException {
        this(0,0,2,2, String.valueOf(Color.RED));
    }

    public Point getTopLeft(){
        return super.getTopLeft();
    }

    public Point getBottomRight(){
        return super.getBottomRight();
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public void setColor(String colorString) throws ColorException {
        this.color = Color.colorFromString(colorString);
        Color.colorEqualsNull(color);
    }

    @Override
    public void setColor(Color color) throws ColorException {
        this.color = color;
        Color.colorEqualsNull(color);
    }

    @Override
    public void stretch(double xRatio, double yRatio) {
        super.stretch((int) xRatio, (int) yRatio);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ColoredRectangle that = (ColoredRectangle) o;
        return color == that.color;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), color);
    }
}