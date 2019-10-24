package five.bits.data.dto;

public class Traffic {
    private Integer a;
    private Integer b;
    private Double jam;

    public Traffic() {
    }

    public Traffic(Integer a, Integer b, Double jam) {
        this.a = a;
        this.b = b;
        this.setJam(jam);
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

    public Double getJam() {
        return jam;
    }

    public void setJam(Double jam) {
        this.jam = jam;
    }

}
