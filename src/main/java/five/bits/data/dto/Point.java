package five.bits.data.dto;

public class Point {
    private String p;
    private Double money;

    public Point() {
    }

    public Point(String p, Double money) {
        this.p = p;
        this.money = money;
    }

    public String getP() {
        return p;
    }

    public void setP(String p) {
        this.p = p;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

}
