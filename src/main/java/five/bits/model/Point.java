package five.bits.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.logging.LoggerGroup;

import java.util.Objects;

public class Point {
    private static final Logger LOGGER = LogManager.getLogger(Point.class);
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
        //LOGGER.debug("set money for point {} from {} to {}", this.getId(), this.money, money);
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

    @Override
    public String toString() {
        return "Point{" +
                "id='" + id + '\'' +
                ", money=" + money +
                '}';
    }
}
