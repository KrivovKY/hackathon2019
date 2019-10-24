package five.bits.data;

public class TeamReconnectMessage {
    private String team;

    public TeamReconnectMessage(String team) {
        this.team = team;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }
}
