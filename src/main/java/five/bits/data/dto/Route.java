package five.bits.data.dto;

public class Route {
    private Integer a;
    private Integer b;
    private Integer time;

    public Route() {
    }

    public Route(Integer a, Integer b, Integer time) {
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

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }
}
