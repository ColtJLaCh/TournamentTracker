package Nodes;

/**PlayerData
 * A class used for easy sorting of all player data, Ids, names, teams and stats,
 * contains a method called getData() that returns all declared properties into an array of Strings
 * @author Colton LaChance
 */
public class PlayerData {

    private final String id;
    private final String name;
    private final String team;

    private final String[] statVals;

    /**
     * PlayerData()
     * @param id
     * @param name
     * @param team
     * @param statsValsToAdd
     * Creates a PlayerData object used to pull an array of Strings from for easy sorting of Player information
     * @author Colton LaChance
     */
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

    /** String[] getData()
     * Pulls all declared properties from PlayerData object and returns their values in an array of Strings
     * @return String[]
     * @author Colton LaChance
     */
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