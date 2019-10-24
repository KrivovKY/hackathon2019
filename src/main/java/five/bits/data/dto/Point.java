package five.bits.data.dto;

public class Point {
    private Integer p;
    private Double money;

    public Point() {
    }

    public Point(Integer p, Double money) {
        this.p = p;
        this.money = money;
    }

    public Integer getP() {
        return p;
    }

    public void setP(Integer p) {
        this.p = p;
    }

    public Integer getMoney() {
        return money.intValue();
    }

    public void setMoney(Double money) {
        this.money = money;
    }

}
