package net.thumbtack.school.figures.v1;

import java.util.Objects;

public class Rectangle {
    private Point center;
    private int width, height;

    public Rectangle(Point center, int width, int height){
        if(width % 2 == 0 && height % 2 ==0)
        {
            this.center = new Point(center.getX(), center.getY());
            this.width = width;
            this.height = height;
        }
    }

    public Rectangle(int xCenter, int yCenter, int width, int height){
        this(new Point(xCenter, yCenter), width, height);
    }

    public Rectangle(int width, int height){
        this(new Point(0, 0), width, height);
    }

    public Rectangle(){
        this(new Point(0, 0), 2, 2);
    }

    public Point getTopLeft(){
        return new Point(center.getX()-(width/2), center.getY()-(height/2));
    }

    public Point getBottomRight(){
        return new Point(center.getX()+(width/2), center.getY()+(height/2));
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
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
        center.setY(center.getY()+dy);
        center.setX(center.getX()+dx);
    }

    public void resize(int ratio)
    {
        int x = center.getX();
        int y = center.getY();
        width*=ratio;
        height*=ratio;
        if(center.getX() != x || center.getY() != y){
           width/=ratio;
           height/=ratio;
        }
    }

    public void stretch(int xRatio, int yRatio)
    {
        int x = center.getX();
        int y = center.getY();
        width*=xRatio;
        height*=yRatio;
        if(center.getX() != x || center.getY() != y){
            width/=xRatio;
            height/=yRatio;
        }
    }

    public double getArea(){
        return getHeight()*getWidth();
    }

    public double getPerimeter(){
        return 2*(getWidth()+getHeight());
    }

    public boolean isInside(int x, int y)
    {
               if(getTopLeft().getX() <= x && getTopLeft().getY() <= y && getBottomRight().getX() >= x && getBottomRight().getY() >= y) {
                   return true;
               }
                 return false;
    }

    public boolean isInside(Point point){
        if(getTopLeft().getX() <= point.getX() && getTopLeft().getY() <= point.getY() &&
                getBottomRight().getX() >= point.getX() && getBottomRight().getY() >= point.getY()) {
            return true;
        }
        return false;
    }

    public boolean isIntersects(Rectangle rectangle){
        return getTopLeft().getX() <= rectangle.getBottomRight().getX() &&
               getBottomRight().getX() >= rectangle.getTopLeft().getX() &&
               getTopLeft().getY() <= rectangle.getBottomRight().getY() &&
               getBottomRight().getY() >= rectangle.getTopLeft().getY();
    }

    public boolean isInside(Rectangle rectangle){
        return  getTopLeft().getX() <= rectangle.getTopLeft().getX() &&
                getBottomRight().getX() >= rectangle.getBottomRight().getX() &&
                getTopLeft().getY() <= rectangle.getTopLeft().getY() &&
                getBottomRight().getY() >= rectangle.getBottomRight().getY();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rectangle rectangle = (Rectangle) o;
        return width == rectangle.width && height == rectangle.height && Objects.equals(center, rectangle.center);
    }

    @Override
    public int hashCode() {
        return Objects.hash(center, width, height);
    }
}
