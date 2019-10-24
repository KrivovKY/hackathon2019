package model;

public class Car {
    private static final Double CAPACITY = 1000000d;
    private String id;
    private Double capacity;

    public Car(String id, Double capacity) {
        this.id = id;
        this.capacity = CAPACITY;
    }

    public Car(String id) {
        this.id = id;
        this.capacity = CAPACITY;
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
        this.capacity = CAPACITY;
    }

}
