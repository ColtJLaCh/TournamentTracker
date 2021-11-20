package Nodes;
public class PlayerData {

    private final String id;
    private final String name;
    private final String team;

    private final String[] statVals;

    public PlayerData(String id, String name, String team, String[] statsValsToAdd) {
        this.id = id;
        this.name = name;
        this.team = team;
        this.statVals = new String[statsValsToAdd.length];
        for (int i = 0; i < statsValsToAdd.length; i++) {
            statVals[i] = statsValsToAdd[i];
        }
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTeam() {
        return team;
    }

    public String getStat(int ind) {
        return statVals[ind];
    }

    public String[] getData() {
        int dataLength = 3+this.statVals.length;
        String[] dataString = new String[dataLength];
        dataString[0] = this.id;
        dataString[1] = this.name;
        dataString[2] = this.team;
        for (int i = 0; i < this.statVals.length; i++){
            dataString[3+i] = this.statVals[i];
        }
        return dataString;
    }
}