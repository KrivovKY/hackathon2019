package five.bits.data;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GotoMessage {

    @JsonProperty("goto")
    private Integer point;
    private String car;

    public GotoMessage(Integer point, String car) {
        this.point = point;
        this.car = car;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public String getCar() {
        return car;
    }

    public void setCar(String car) {
        this.car = car;
    }
}
