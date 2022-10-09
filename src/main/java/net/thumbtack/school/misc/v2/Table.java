package net.thumbtack.school.misc.v2;

import net.thumbtack.school.iface.v2.HasArea;

import java.util.Objects;

public class Table implements HasArea
{
    private double area;

    public Table(double area){
        this.area = area;
    }

    public Table(){
        this(5);
    }

    public void setArea(double area){
        this.area = area;
    }
    @Override
    public double getArea(){
        return area;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Table table = (Table) o;
        return Double.compare(table.area, area) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(area);
    }
}
