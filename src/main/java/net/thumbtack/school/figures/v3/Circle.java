package net.thumbtack.school.figures.v3;
import java.util.Objects;

public class Circle extends Figure
{
    private int radius;

    public Circle(Point center, int radius) {
        this(center.getX(), center.getY(), radius);
    }

    public Circle(int xCenter, int yCenter, int radius)
    {
        super(xCenter, yCenter);
        this.radius = radius;
    }

    public Circle(int radius) {
        this(0,0, radius);
    }

    public Circle() {
        this(0,0,1);
    }

    public Point getCenter() {
        return new Point(center.getX(), center.getY());
    }

    public int getRadius() {
        return radius;
    }

    public void setCenter(Point center){
        this.center.setX(center.getX());
        this.center.setY(center.getY());
    }

    public void setRadius(int radius){
        this.radius = radius;
    }

    @Override
    public void moveTo(int x, int y){
        this.center.setX(x);
        this.center.setY(y);
    }

    @Override
    public void moveTo(Point point){
        super.moveTo(point);
    }

    @Override
    public void moveRel(int dx, int dy){
        this.center.setX(center.getX()+dx);
        this.center.setY(center.getY()+dy);
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
        return radius >= Math.pow(Math.pow(x - center.getX(), 2) + Math.pow(y - center.getY(), 2), 0.5);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Circle circle = (Circle) o;
        return center.getX() == circle.center.getX() &&
                center.getY() == circle.center.getY() &&
                radius == circle.radius;
    }

    @Override
    public int hashCode() {
        return Objects.hash(center.getX(), center.getY(), radius);
    }
}