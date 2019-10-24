package five.bits.data.dto;

public class Route {
    private Integer a;
    private Integer b;
    private Double time;

    public Route() {
    }

    public Route(Integer a, Integer b, Double time) {
        this.a = a;
        this.b = b;
        this.time = time;
    }

    public Integer getA() {
        return a;
    }

    public void setA(Integer a) {
        this.a = a;
    }

    public Integer getB() {
        return b;
    }

    public void setB(Integer b) {
        this.b = b;
    }

    public Double getTime() {
        return time;
    }

    public void setTime(Double time) {
        this.time = time;
    }
}
