package five.bits.model;

public class Traffic {
    private Point from;
    private Point to;
    private Double jam;

    public Traffic(Point from, Point to, Double jam) {
        this.from = from;
        this.to = to;
        this.jam = jam;
    }

    public Point getFrom() {
        return from;
    }

    public void setFrom(Point from) {
        this.from = from;
    }

    public Point getTo() {
        return to;
    }

    public void setTo(Point to) {
        this.to = to;
    }

    public Double getJam() {
        return jam;
    }

    public void setJam(Double jam) {
        this.jam = jam;
    }
}
