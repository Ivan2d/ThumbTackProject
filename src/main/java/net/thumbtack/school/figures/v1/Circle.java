package net.thumbtack.school.figures.v1;
import java.util.Objects;

public class Circle {
    private Point center = new Point();
    private int radius;

    public Circle(Point center, int radius){
        this.center = center;
        this.radius = radius;
    }

    public Circle(int xCenter, int yCenter, int radius){
        center.setX(xCenter);
        center.setY(yCenter);
        this.radius = radius;
    }

    public Circle(int radius){
        center.setX(0);
        center.setY(0);
        this.radius = radius;
    }

    public Circle(){
        center.setX(0);
        center.setY(0);
        this.radius = 1;
    }

    public Point getCenter(){
        return center;
    }

    public void setCenter(Point point){
        this.center = point;
    }

    public int getRadius(){
        return radius;
    }

    public void setRadius(int radius){
        this.radius = radius;
    }

    public void moveTo(int x, int y){
        center.setX(x);
        center.setY(y);
    }

    public void moveTo(Point point){
        center.setX(point.getX());
        center.setY(point.getY());
    }

    public void moveRel(int dx, int dy){
        center.setX(center.getX() + dx);
        center.setY(center.getY() + dy);
    }

    public void resize(int ratio){
        setRadius(getRadius()*ratio);
    }

    public double getArea(){
        return Math.PI*radius*radius;
    }

    public double getPerimeter(){
        return 2*Math.PI*radius;
    }

    public boolean isInside(int x, int y){
        return Math.sqrt((x - center.getX())*(x - center.getX()) +
                (y - center.getY())*(y - center.getY())) <= radius;
    }

    public boolean isInside(Point point){
        return Math.sqrt((point.getX() - center.getX())*(point.getX() - center.getX()) +
                (point.getY() - center.getY())*(point.getY() - center.getY())) <= radius;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Circle circle = (Circle) o;
        return radius == circle.radius && Objects.equals(center, circle.center);
    }

    @Override
    public int hashCode() {
        return Objects.hash(center, radius);
    }

}
