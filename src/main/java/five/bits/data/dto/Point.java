package five.bits.data.dto;

public class Point {
    private String p;
    private Integer money;

    public Point() {
    }

    public Point(String p, Integer money) {
        this.p = p;
        this.money = money;
    }

    public String getP() {
        return p;
    }

    public void setP(String p) {
        this.p = p;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

}
