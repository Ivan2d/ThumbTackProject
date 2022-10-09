package net.thumbtack.school.figures.misc.v3;

import net.thumbtack.school.misc.v2.Table;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class TestTable {
    @Test
    public void testTable() {
        Table table = new Table(1);
        assertEquals(1, table.getArea());
    }

    @Test
    public void setTable() {
        Table table = new Table();
        assertEquals(5, table.getArea());
        table.setArea(3);
        assertNotEquals(1, table.getArea());
    }

    @Test
    public void testEqualsTable() {
        Table table1 = new Table(1);
        Table table2 = new Table(1);
        Table table3 = new Table(3);
        assertEquals(table1, table2);
        assertNotEquals(table1, table3);
    }
}

