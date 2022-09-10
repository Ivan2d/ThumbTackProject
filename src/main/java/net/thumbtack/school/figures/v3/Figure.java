package net.thumbtack.school.figures.v3;
import net.thumbtack.school.iface.v3.HasArea;
import net.thumbtack.school.iface.v3.Movable;
import net.thumbtack.school.iface.v3.Resizable;

abstract public class Figure implements Movable, Resizable, HasArea
{
    public Point center;
    public void moveTo(int x, int y){
        center.setX(x);
        center.setY(y);
    }

    public void moveRel(int dx, int dy)
    {
        center.setX(center.getX()+dx);
        center.setY(center.getY()+dy);
    }

    public abstract void resize(double ratio);

    public abstract double getArea();

    public abstract double getPerimeter();

    public Point getCenter(){
        return center;
    }
    public abstract boolean isInside(int x, int y);

    public boolean isInside(Point point)
    {
        return isInside(point.getX(), point.getY());
    }
}