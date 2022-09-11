package net.thumbtack.school.boxes.v3;

import net.thumbtack.school.figures.v3.Figure;
import net.thumbtack.school.iface.v3.HasArea;

public class Box<T extends Figure> implements HasArea
{
    private T obj;
    public Box(T obj){
        this.obj = obj;
    }

    public T getContent(){
        return obj;
    }

    public void setContent(T obj){
        this.obj = obj;
    }

    public boolean isAreaEqual(Box<?> box){
        return Math.abs(this.obj.getArea() - box.getArea()) < 0.0001;
    }

    public static boolean isAreaEqual(Box<?> box1, Box<?> box2){
        return Math.abs(box1.getArea() - box2.getArea()) < 0.0001;
    }

    @Override
    public double getArea() {
        return obj.getArea();
    }
}
