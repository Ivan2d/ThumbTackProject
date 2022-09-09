package net.thumbtack.school.figures.v1;

import java.util.Objects;

public class Square {
    private Point center;
    private int size;

    public Square(Point center, int size){
        if(size % 2 == 0 && size > 0){
            this.center = center;
            this.size = size;
        }
    }

    public Square(int xCenter, int yCenter, int size){
        this(new Point(xCenter,yCenter), size);
    }

    public Square(int size){
        this(new Point(0,0), size);
    }

    public Square(){
        this(new Point(0,0), 2);
    }

    public Point getTopLeft(){
        return new Point(center.getX() - (size/2), center.getY() - (size/2));
    }

    public Point getBottomRight(){
        return new Point(center.getX() + (size/2), center.getY() + (size/2));
    }

    public int getLength(){
        return size;
    }

    public Point getCenter(){
        return center;
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
        center.setX(center.getX()+dx);
        center.setY(center.getY()+dy);
    }

    public void resize(int ratio)
    {
        int x = center.getX();
        int y = center.getY();
        size*=ratio;
        if(center.getX() != x || center.getY() != y){
            size/=ratio;
        }
    }

    public double getArea(){
        return size*size;
    }

    public double getPerimeter(){
        return 4*size;
    }

    public boolean isInside(int x, int y)
    {
        if(getTopLeft().getX() <= x && getTopLeft().getY() <= y && getBottomRight().getX() >= x && getBottomRight().getY() >= y) {
            return true;
        }
        return false;
    }

    public boolean isInside(Point point){
        if(getTopLeft().getX() <= point.getX() && getTopLeft().getY() <= point.getY() && getBottomRight().getX() >= point.getX() && getBottomRight().getY() >= point.getY()) {
            return true;
        }
        return false;
    }
    public boolean isIntersects(Square square)
    {
        return getTopLeft().getX() <= square.getBottomRight().getX() &&
                getBottomRight().getX() >= square.getTopLeft().getX() &&
                getTopLeft().getY() <= square.getBottomRight().getY() &&
                getBottomRight().getY() >= square.getTopLeft().getY();
    }

    public boolean isInside(Square square){
        return  getTopLeft().getX() <= square.getTopLeft().getX() &&
                getBottomRight().getX() >= square.getBottomRight().getX() &&
                getTopLeft().getY() <= square.getTopLeft().getY() &&
                getBottomRight().getY() >= square.getBottomRight().getY();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Square square = (Square) o;
        return size == square.size && Objects.equals(center, square.center);
    }

    @Override
    public int hashCode() {
        return Objects.hash(center, size);
    }

}
