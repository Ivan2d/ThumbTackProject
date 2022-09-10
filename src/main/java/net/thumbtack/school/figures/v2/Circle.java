package net.thumbtack.school.figures.v2;

import java.util.Objects;

public class Circle extends Figure
{
    private int xCenter, yCenter, radius;

    public Circle(Point center, int radius) {
        this(center.getX(), center.getY(), radius);
    }

    public Circle(int xCenter, int yCenter, int radius) {
        this.xCenter = xCenter;
        this.yCenter = yCenter;
        this.radius = radius;
    }

    public Circle(int radius) {
        this(0,0, radius);
    }

    public Circle() {
        this(0,0,1);
    }

    public Point getCenter() {
        return new Point(xCenter, yCenter);
    }

    public int getRadius() {
        return radius;
    }

    public void setCenter(Point center){
        this.xCenter = center.getX();
        this.yCenter = center.getY();
    }

    public void setRadius(int radius){
        this.radius = radius;
    }

    @Override
    public void moveTo(int x, int y){
        xCenter = x;
        yCenter = y;
    }

    @Override
    public void moveTo(Point point){
        super.moveTo(point);
    }

    @Override
    public void moveRel(int dx, int dy){
        xCenter += dx;
        yCenter += dy;
    }

    @Override
    public void resize(double ratio){
        radius = (int)(radius * ratio);
    }

    @Override
    public double getArea(){
        return Math.PI * radius * radius;
    }

    @Override
    public double getPerimeter(){
        return 2 * Math.PI * radius;
    }

    @Override
    public boolean isInside(int x, int y)
    {
        return radius >= Math.pow(Math.pow(x - xCenter, 2) + Math.pow(y - yCenter, 2), 0.5);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Circle circle = (Circle) o;
        return xCenter == circle.xCenter &&
                yCenter == circle.yCenter &&
                radius == circle.radius;
    }

    @Override
    public int hashCode() {
        return Objects.hash(xCenter, yCenter, radius);
    }
}