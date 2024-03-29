package five.bits.data.dto;

public class Car {
    public static final Double MAX_CAPACITY = 1000000d;
    private String id;
    private Double capacity;

    public Car(String id, Double capacity) {
        this.id = id;
        this.capacity = capacity;
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

}
