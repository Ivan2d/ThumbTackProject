package net.thumbtack.school.figures.v2;
import net.thumbtack.school.iface.v2.Colored;
import net.thumbtack.school.iface.v2.HasArea;
import net.thumbtack.school.iface.v2.Stretchable;

import java.util.Objects;

public class ColoredRectangle extends Rectangle implements Colored, Stretchable, HasArea
{
    private int color;

    public ColoredRectangle(Point center, int width, int height, int color){
        super(center, width, height);
        this.color = color;
    }

    public ColoredRectangle(int xCenter, int yCenter, int width, int height, int color){
        this(new Point(xCenter, yCenter), width, height, color);
    }

    public ColoredRectangle(int width, int height, int color){
        this(new Point(0,0), width, height, color);
    }

    public ColoredRectangle(int color){
        this(new Point(0,0), 2, 2, color);
    }

    public ColoredRectangle(){
        this(0,0,2,2, 1);
    }

    public Point getTopLeft(){
        return super.getTopLeft();
    }

    public Point getBottomRight(){
        return super.getBottomRight();
    }

    @Override
    public int getColor() {
        return color;
    }

    @Override
    public void setColor(int color) {
        this.color = color;
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