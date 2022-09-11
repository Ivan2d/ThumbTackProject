package net.thumbtack.school.figures.v2;

public class Rectangle extends Figure {

    private int width, height;

    public Rectangle(int xCenter, int yCenter, int width, int height){
        this(new Point(xCenter, yCenter), width, height);
    }

    public Rectangle(Point center, int width, int height){
        super(center);
        if(width % 2 == 0 && height % 2 ==0)
        {
            this.width = width;
            this.height = height;
        }
    }

    public Rectangle(int height, int width){//3
        this(new Point(0,0), height, width);
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

    public int getHeight() {
        return height;
    }

    public int getWidth() {//10
        return width;
    }

    @Override
    public void moveTo(int x, int y){
        center.setX(x);
        center.setY(y);
    }

    @Override
    public void moveTo(Point point){
        super.moveTo(point);
    }

    @Override
    public Point getCenter(){
        return center;
    }

    @Override
    public void moveRel(int dx, int dy){
        center.setY(center.getY()+dy);
        center.setX(center.getX()+dx);
    }

    @Override
    public void resize(double ratio)
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

    @Override
    public boolean isInside(int x, int y)
    {
        if(getTopLeft().getX() <= x && getTopLeft().getY() <= y && getBottomRight().getX() >= x && getBottomRight().getY() >= y) {
            return true;
        }
        return false;
    }


    @Override
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

        if (width != rectangle.width) return false;
        return height == rectangle.height;
    }

    @Override
    public int hashCode() {
        int result = width;
        result = 31 * result + height;
        return result;
    }
}