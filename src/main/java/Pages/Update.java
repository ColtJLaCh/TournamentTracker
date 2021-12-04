package Pages;

import Database.Database;
import Nodes.PlayerData;
import Nodes.TourTab;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

/** PAGE CLASS
 * Constructor contains all layout information, add methods and properties as needed for functionality
 */
public class Update extends Page {

    //Anything with functionality goes here, Buttons, TextFields etc... as well as needed globals
    TourTab parentTab;

    Database dbc = Database.getInstance();
    ChoiceBox teamChoiceBox;
    ChoiceBox playerChoiceBox;
    ChoiceBox statChoiceBox;
    TextField updateVal;
    Button updateButton;

    HBox layoutHBox;

    VBox playerVBox;
    VBox teamsVBox;
    VBox statsVBox;
    VBox valueVBox;
    VBox buttonVBox;

    //Data -- The globals containing all player data to be inserted into a PlayerData object
    ObservableList<String[]> playerData;

    //Data
    String dataTourName = "";
    String[] dataTeamArr; //The ordered array of teams to be matched up to players
    ArrayList<String> uniqueTeams = new ArrayList<String>(); //An arrayList containing only unique values of teams
    String[] dataPlayerArr; //The ordered array of players to matched up to teams
    String[] dataStats; //if teams stats 0 and 1 (Wins/Losses) will be applied to team, all others will be applied to players
    String[][] dataStatsArr; // A 2d array for tracking the stats of each player
    int dataSets = 2; //2 is default set count, sets are the same for all players, so no need to store data in array
    int dataTourStyle = 0; //0 is singles, 1 is teams, not calculated til after data has been received

    public Update(TourTab parentTab, String tourName) {
        //Initialize layout assets here, ImageViews, Panes, Text etc...
        this.parentTab = parentTab;
        dataTourName = tourName;

        collectData();
        insertData();

        layoutHBox = new HBox(24);

        teamsVBox = new VBox(2);
        playerVBox = new VBox(2);
        statsVBox = new VBox(2);
        valueVBox = new VBox(2);
        buttonVBox = new VBox(2);
        if (dataTourStyle == 1) {
            Label teamsLabel = new Label("Which Team?");
            teamsLabel.setUnderline(true);
            ObservableList<String> teamList = FXCollections.observableArrayList(uniqueTeams);
            teamChoiceBox = new ChoiceBox(teamList);
            teamChoiceBox.setValue(teamList.get(0));
            teamsVBox.getChildren().addAll(teamsLabel,teamChoiceBox);
            layoutHBox.getChildren().add(teamsVBox);

            Label playerLabel = new Label("Which Player?");
            playerLabel.setUnderline(true);
            ObservableList<String> playerList = FXCollections.observableArrayList();
            playerList.add("FULL TEAM");
            for (int p = 0; p < dataPlayerArr.length; p++) {
               if (playerData.get(p)[2].equals(teamChoiceBox.getSelectionModel().getSelectedItem().toString())) {
                    playerList.add(playerData.get(p)[1]);
               }
            }

            playerChoiceBox = new ChoiceBox(playerList);
            playerVBox.getChildren().addAll(playerLabel,playerChoiceBox);
            layoutHBox.getChildren().add(playerVBox);

            teamChoiceBox.setOnAction(e-> {
                playerChoiceBox.setValue(null);
                playerList.clear();

                ObservableList<String> playerListFinal = playerList;
                playerListFinal.add("FULL TEAM");
                for (int p = 0; p < dataPlayerArr.length; p++) {
                    if (playerData.get(p)[2].equals(teamChoiceBox.getSelectionModel().getSelectedItem().toString())) {
                        playerListFinal.add(playerData.get(p)[1]);
                    }
                }
                playerChoiceBox = new ChoiceBox(playerListFinal);

                playerVBox.getChildren().clear();
                playerVBox.getChildren().addAll(playerLabel,playerChoiceBox);

                reconstructHBox();
                reconstructClassVBox(layoutHBox);
            });


        }else{
            Label playerLabel = new Label("Which Player?");
            playerLabel.setUnderline(true);
            ObservableList<String> playerList = FXCollections.observableArrayList(dataPlayerArr);
            playerChoiceBox = new ChoiceBox(playerList);
            playerVBox.getChildren().addAll(playerLabel,playerChoiceBox);
            layoutHBox.getChildren().add(playerVBox);
        }

        Label statLabel = new Label("Which Property?");
        statLabel.setUnderline(true);

        ObservableList<String> statList;
        if (playerChoiceBox.getSelectionModel().getSelectedItem() != null &&
                playerChoiceBox.getSelectionModel().getSelectedItem().toString().equals("FULL TEAM")) {
            statList = FXCollections.observableArrayList("TeamName","Wins","Losses");
        }else {
            if (dataTourStyle == 1) {
                statList = FXCollections.observableArrayList("Player", "TeamName");
            }else{
                statList = FXCollections.observableArrayList("Player","TeamName","Wins","Losses");
            }
        }
        for (int st = 2; st < dataStats.length; st++) {
            statList.add(dataStats[st]);
        }
        statChoiceBox = new ChoiceBox(statList);
        statsVBox.getChildren().addAll(statLabel,statChoiceBox);
        layoutHBox.getChildren().add(statsVBox);

        playerChoiceBox.setOnAction(e-> {
            statList.clear();

            ObservableList<String> statListFinal = statList;
            if (playerChoiceBox.getSelectionModel().getSelectedItem() != null &&
                    playerChoiceBox.getSelectionModel().getSelectedItem().toString().equals("FULL TEAM")) {
                statListFinal = FXCollections.observableArrayList("TeamName","Wins","Losses");
            }else {
                if (dataTourStyle == 1) {
                    statListFinal = FXCollections.observableArrayList("Player", "TeamName");
                }else{
                    statListFinal = FXCollections.observableArrayList("Player","TeamName","Wins","Losses");
                }
            }

            for (int st = 2; st < dataStats.length; st++) {
                statListFinal.add(dataStats[st]);
            }

            statChoiceBox = new ChoiceBox(statListFinal);

            statsVBox.getChildren().clear();
            statsVBox.getChildren().addAll(statLabel,statChoiceBox);

            reconstructHBox();
            reconstructClassVBox(layoutHBox);
        });

        Label valueLabel = new Label("With what value?");
        valueLabel.setUnderline(true);
        updateVal = new TextField();
        updateVal.setPromptText("Value...");
        valueVBox.getChildren().addAll(valueLabel,updateVal);

        Label buttonLabel = new Label("Update with parameters");
        buttonLabel.setUnderline(true);
        updateButton = new Button("Update");
        buttonVBox.getChildren().addAll(buttonLabel,updateButton);

        updateButton.setOnMouseClicked(ev -> {
            Connection conn = dbc.getConnection();
            String playerName = playerChoiceBox.getSelectionModel().getSelectedItem().toString();
            String[] player = new String[playerData.get(0).length];
            if (!playerName.equals("FULL TEAM")) {
                for (String[] data : playerData) {
                    if (data[1].equals(playerName))
                        player = data;
                }
                int pID = Integer.parseInt(player[0])+1;
                try {
                    dbc.updatePlayer(dataTourName,
                            pID,
                            new String[]{statChoiceBox.getSelectionModel().getSelectedItem().toString()},
                            new String[]{updateVal.getText()},
                            conn);
                } catch (SQLException e) {
                    System.out.println("Error updating tables.");
                }
            }else{
                for (int p = 0; p < dataPlayerArr.length; p++) {
                    if (playerData.get(p)[2].equals(teamChoiceBox.getSelectionModel().getSelectedItem().toString())) {
                        int pID = Integer.parseInt(playerData.get(p)[0])+1;
                        try {
                            dbc.updatePlayer(dataTourName,
                                    pID,
                                    new String[]{statChoiceBox.getSelectionModel().getSelectedItem().toString()},
                                    new String[]{updateVal.getText()},
                                    conn);
                        } catch (SQLException e) {
                            System.out.println("Error updating tables.");
                        }
                    }
                }
            }
        });

        layoutHBox.getChildren().addAll(valueVBox,buttonVBox);
        classVBox = new VBox(layoutHBox); //Vbox needed for Top to Bottom layout, add assets here
        //classVBox.setAlignment(ALIGNMENT GOES HERE); //Usually Pos.TOP_LEFT
        classVBox.setPadding(new Insets(10,10,10,10)); //Set padding for Vbox (ORDER : double top, double right, double bottom, double left)
        classVBox.setSpacing(10); //Set spacing here

        classPane.setTop(classVBox);//Set it to top to place all content directly under menu
    }

    //Local methods
    private void collectData() {
        Connection conn = dbc.getConnection();
        try {
            System.out.println(dataTourName);
            ResultSet tourData = dbc.getTable(dataTourName,conn);
            ResultSetMetaData tdmd = tourData.getMetaData();

            int rows = 0;
            tourData.last();
            rows = tourData.getRow();
            tourData.beforeFirst();

            dataStats = new String[tdmd.getColumnCount()-4];
            dataPlayerArr = new String[rows];
            dataTeamArr = new String[rows];
            dataStatsArr = new String[dataPlayerArr.length][dataStats.length];

            int row = 0;

            while (tourData.next()) {
                for (int st = 0; st < dataStats.length; st++) {
                    dataStats[st] = tdmd.getColumnName(st+5);
                    dataStatsArr[row][st] = tourData.getString(dataStats[st]);
                }

                dataPlayerArr[row] = tourData.getString("Player");
                dataTeamArr[row] = tourData.getString("TeamName");

                dataSets = Integer.parseInt(tourData.getString("Sets"));
                row += 1;
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving tables.");
        }
    }

    /**insertData()
     * Inserts fetched data from some globals into PlayerData objects
     * @author Colton LaChance
     */
    private void insertData() {
        String lastTeam = "";
        for (String team : dataTeamArr) {
            if (!team.equals("NO TEAM")) dataTourStyle = 1;

            if (!lastTeam.equals(team)) {
                uniqueTeams.add(team);
                lastTeam = team;
            }
        }

        playerData = FXCollections.observableArrayList();
        for (int p = 0; p < dataPlayerArr.length; p++) {
            String pId = String.valueOf(p);
            String[] dataToAdd = new PlayerData(pId, dataPlayerArr[p],dataTeamArr[p],dataStatsArr[p]).getData();
            playerData.add(dataToAdd);
        }
    }

    private void reconstructHBox() {
        layoutHBox.getChildren().clear();
        if (dataTourStyle == 0) {
            layoutHBox.getChildren().addAll(playerVBox);
        }else{
            layoutHBox.getChildren().addAll(teamsVBox,playerVBox);
        }
        layoutHBox.getChildren().addAll(statsVBox,valueVBox,buttonVBox);
    }

    //Methods to add to pageBehavior


    //Use this inherited method to call all methods related to class needed for functionality
    @Override
    public void pageBehavior() {

    }
}
