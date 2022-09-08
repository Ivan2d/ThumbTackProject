package net.thumbtack.school.figures.v1;

import java.util.Objects;

public class Ellipse {
    private Point center = new Point();
    private int xAxis;
    private int yAxis;

    public Ellipse(Point center, int xAxis, int yAxis){
        this.center = center;
        this.xAxis = xAxis;
        this.yAxis = yAxis;
    }

    public Ellipse(int xCenter, int yCenter, int xAxis, int yAxis){
        center.setX(xCenter);
        center.setY(yCenter);
        this.xAxis = xAxis;
        this.yAxis = yAxis;
    }

    public Ellipse(int xAxis, int yAxis){
        center.setX(0);
        center.setY(0);
        this.xAxis = xAxis;
        this.yAxis = yAxis;
    }

    public Ellipse(){
        center.setX(0);
        center.setY(0);
        this.xAxis = 1;
        this.yAxis = 1;
    }

    public Point getCenter(){
        return center;
    }

    public void setCenter(Point point){
        this.center = point;
    }

    public int getXAxis(){
        return xAxis;
    }

    public int getYAxis(){
        return yAxis;
    }

    public void setXAxis(int xAxis){
        this.xAxis = xAxis;
    }

    public void setYAxis(int yAxis){
        this.yAxis = yAxis;
    }

    public void moveTo(int x, int y){
        center.setY(y);
        center.setX(x);
    }

    public void moveTo(Point point){
        center.setY(point.getY());
        center.setX(point.getX());
    }

    public void moveRel(int dx, int dy){
        center.setY(center.getY() + dy);
        center.setX(center.getX() + dx);
    }

    public void resize(int ratio){
        setXAxis(getXAxis()*ratio);
        setYAxis(getYAxis()*ratio);
    }

    public void stretch(int xRatio, int yRatio){
        setXAxis(getXAxis()*xRatio);
        setYAxis(getYAxis()*yRatio);
    }

    public double getArea(){
        return Math.PI * xAxis * yAxis/4;
    }

    public double getPerimeter(){
        return 2 * Math.PI * Math.sqrt((xAxis*xAxis + yAxis*yAxis)*0.125);
    }

    public boolean isInside(int x, int y)
    {
        return Math.pow((x-center.getX()),2)/Math.pow(yAxis, 2) + Math.pow(y-center.getY(),2)/Math.pow(xAxis, 2) <= 1;
    }

    public boolean isInside(Point point){
        return Math.pow((point.getX()-center.getX()),2)/Math.pow(yAxis, 2) + Math.pow(point.getY()-center.getY(),2)/Math.pow(xAxis, 2) <= 1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ellipse ellipse = (Ellipse) o;
        return xAxis == ellipse.xAxis && yAxis == ellipse.yAxis && Objects.equals(center, ellipse.center);
    }

    @Override
    public int hashCode() {
        return Objects.hash(center, xAxis, yAxis);
    }
}
