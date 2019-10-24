package bit.hackathonendpoint.data;

public class Route {
    private String from;
    private String to;
    private Double time;

    public Route(String from, String to, Double time) {
        this.from = from;
        this.to = to;
        this.time = time;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Double getTime() {
        return time;
    }

    public void setTime(Double time) {
        this.time = time;
    }


}
