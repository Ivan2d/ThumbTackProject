package net.thumbtack.school.figures.v3;

public class Square extends Figure {

    private int size;

    public Square(Point center, int size){
        super(center);
        if(size % 2 == 0 && size > 0){
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

    @Override
    public void moveTo(int x, int y){
        center.setX(x);
        center.setY(y);
    }

    public void moveTo(Point point){
        super.moveTo(point);
    }

    @Override
    public void moveRel(int dx, int dy){
        center.setX(center.getX()+dx);
        center.setY(center.getY()+dy);
    }

    @Override
    public void resize(double ratio) {
        int x = center.getX();
        int y = center.getY();
        size*=ratio;
        if(center.getX() != x || center.getY() != y){
            size/=ratio;
        }
    }


    @Override
    public double getArea(){
        return size*size;
    }

    @Override
    public double getPerimeter(){
        return 4*size;
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

        return size == square.size;
    }

    @Override
    public int hashCode() {
        return size;
    }
}