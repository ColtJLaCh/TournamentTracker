package Nodes;

public class PlayerData {

    private String id;
    private String name;
    private String team;
    private String[] stats;

    private PlayerData(String id, String name, String team, String... statsToAdd) {
        this.id = id;
        this.name = name;
        this.team = team;
        for (int i = 0; i < stats.length; i++) {
            this.stats[i] = statsToAdd[i];
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String[] getStats() {
        return stats;
    }

    public void setStats(String[] stats) {
        this.stats = stats;
    }
}