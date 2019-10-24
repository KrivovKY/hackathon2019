package bit.hackathonendpoint.data;

public class Traffic {
    private String from;
    private String to;
    private Double jam;

    public Traffic(String from, String to, Double jam) {
        this.from = from;
        this.to = to;
        this.jam = jam;
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

    public Double getJam() {
        return jam;
    }

    public void setJam(Double jam) {
        this.jam = jam;
    }
}
