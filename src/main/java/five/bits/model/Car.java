package five.bits.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;

import static five.bits.model.MapHandler.TOTAL_TIME;

public class Car {
    private static final Logger LOGGER = LogManager.getLogger(Car.class);

    public static final Double MAX_CAPACITY = 1000000d;
    //public static final Double MAX_CAPACITY = 200000d;
    private String id;
    private Double capacity;
    private Double restTime;

    public Car(String id, Double capacity) {
        this.id = id;
        this.capacity = capacity;
        this.restTime = TOTAL_TIME;
    }

    public Car(String id) {
        this.id = id;
        this.capacity = 0d;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getCapacity() {
        return capacity;
    }

    public void setCapacity(Double capacity) {
        this.capacity = capacity;
    }

    public Double getRestTime() {
        return restTime;
    }

    public void reduceRestTime(Double time) {
        this.restTime = this.restTime - time;
        LOGGER.debug("reduce rest time for car {} to {}", this.getId(), this.restTime);
    }

    public void setRestTime(Double restTime) {
        this.restTime = restTime;
    }

    public Double getRestCapacity() {
        return MAX_CAPACITY - this.capacity;
    }

    public void addCapacity(Double money) {
        this.capacity = this.capacity + money;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return id.equals(car.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Car{" +
                "id='" + id + '\'' +
                ", capacity=" + capacity +
                ", restTime=" + restTime +
                '}';
    }
}
