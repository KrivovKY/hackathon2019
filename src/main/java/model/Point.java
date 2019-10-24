package model;

import java.util.Objects;

public class Point {
    private String id;
    private Double money;

    public Point(String id, Double money) {
        this.id = id;
        this.money = money;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return id.equals(point.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
