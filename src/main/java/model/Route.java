package model;

public class Route {
    private Point from;
    private Point to;
    private Double time;

    public Route(Point from, Point to, Double time) {
        this.from = from;
        this.to = to;
        this.time = time;
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

    public Double getTime() {
        return time;
    }

    public void setTime(Double time) {
        this.time = time;
    }


}
