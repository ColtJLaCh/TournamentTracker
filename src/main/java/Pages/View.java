package Pages;

import HelpfulClasses.UsefulConstants;
import Nodes.PlayerData;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.css.Style;
import javafx.geometry.Insets;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Callback;

import java.lang.reflect.Array;
import java.util.*;

/** PAGE CLASS
 * Constructor contains all layout information, add methods and properties as needed for functionality
 */
public class View extends Page {

    //Anything with functionality goes here, Buttons, TextFields etc... as well as needed globals

    //Data
    String dataTourName = "";
    String[] dataTeamArr; //The ordered array of teams to be matched up to players
    ArrayList<String> uniqueTeams = new ArrayList<String>(); //An arrayList containing only unique values of teams
    String[] dataPlayerArr; //The ordered array of players to matched up to teams
    String[] dataStats; //if teams stats 0 and 1 (Wins/Losses) will be applied to team, all others will be applied to players
    String[][] dataStatsArr; // A 2d array for tracking the stats of each player
    int dataSets = 2; //2 is default set count, sets are the same for all players, so no need to store data in array
    int dataTourStyle = 0; //0 is singles, 1 is teams, not calculated til after data has been received

    TableView<String>[] brackets;
    ObservableList<String[]> playerData;
    ArrayList<String> columns = new ArrayList<String>();

    public View() {
        //Initialize layout assets here, ImageViews, Panes, Text etc...

        dummyData(); //TODO: Replace this with retrieve method when functionality is added
        insertData();

        Text tourTypeText = new Text("TYPE: " + (dataTourStyle == 0 ? "SINGLES TOURNAMENT" : "TEAMS TOURNAMENT"));

        classVBox = new VBox(tourTypeText); //Vbox needed for Top to Bottom layout, add assets here

        createBrackets();

        //classVBox.setAlignment(ALIGNMENT GOES HERE); //Usually Pos.TOP_LEFT
        classVBox.setPadding(new Insets(10,10,10,10)); //Set padding for Vbox (ORDER : double top, double right, double bottom, double left)
        classVBox.setSpacing(10); //Set spacing here

        classPane.setTop(classVBox);//Set it to top to place all content directly under menu
    }

    //Local methods
    private void dummyData() {
        //Fake table data, this will be injected into these variables when functionality is programmed

        //Singles
        ///*
        dataTourName = "DummyTournamentSINGLES";
        dataStats = new String[]{"Wins", "Losses", "Time"};
        dataPlayerArr = new String[]{"Jesse", "Jamie", "Bobby", "Billy", "Brody", "Mort"};
        dataTeamArr = new String[]{"NO TEAM", "NO TEAM", "NO TEAM", "NO TEAM", "NO TEAM", "NO TEAM"};
        dataStatsArr = new String[dataPlayerArr.length][dataStats.length];
        dataSets = 2;
        //*/

        //Teams
        /*
        dataTourName = "DummyTournamentTEAM";
        dataStats = new String[] {"Wins", "Losses", "Time"};
        dataPlayerArr = new String[]{"Jesse","Jamie","Bobby","Billy","Brody","Mort","Sherry","Donkey"};
        dataTeamArr = new String[]{"TEAM1","TEAM2","TEAM3","TEAM4","TEAM5","TEAM6","TEAM7","TEAM8"};
        dataStatsArr = new String[dataPlayerArr.length][dataStats.length];
        dataSets = 2;
        */

        for (int p = 0; p < dataPlayerArr.length; p++) {
            for (int s = 0; s < dataStats.length; s++) {
                dataStatsArr[p][s] = "0";
            }
        }
    }

    private void insertData() {
        String lastTeam = "";
        for (String team : dataTeamArr) {
            if (team != "NO TEAM") dataTourStyle = 1;

            if (lastTeam != team) {
                uniqueTeams.add(team);
                lastTeam = team;
            }
        }

        int amntOfSets = dataSets;
        if (dataTourStyle == 0) {
            amntOfSets = (dataPlayerArr.length+dataSets-1)/dataSets;
        }else{
            if (!uniqueTeams.isEmpty())
                amntOfSets = (uniqueTeams.size()+dataSets-1)/dataSets;
        }

        brackets = new TableView[amntOfSets];

        playerData = FXCollections.observableArrayList();
        for (int t = 0; t < uniqueTeams.size(); t++) {
            for (int p = 0; p < dataPlayerArr.length; p++) {
                if (dataTourStyle == 0) {
                    String pId = String.valueOf(p);
                    String[] dataToAdd = new PlayerData(pId, dataPlayerArr[p],"NO TEAM",dataStatsArr[p]).getData();
                    playerData.add(dataToAdd);
                }else{
                    String tId = String.valueOf(t);
                    String[] dataToAdd = new PlayerData(tId, dataPlayerArr[p], uniqueTeams.get(t),dataStatsArr[p]).getData();
                    playerData.add(dataToAdd);
                }
            }
        }

        columns.add("Id");
        columns.add("Name");
        columns.add("Team");
        for (int cToAdd = 0; cToAdd < dataStats.length; cToAdd++) {
            columns.add(dataStats[cToAdd]);
        }
    }

    private void createBrackets() {
        int bracketNum = 0;
        int lastTeamInd = 0;
        for (TableView bracket : brackets) {
            ObservableList<String[]> bracketData = FXCollections.observableArrayList();
            bracket = new TableView();

            bracket.setMaxWidth(UsefulConstants.DEFAULT_SCREEN_WIDTH/3);
            bracket.setFixedCellSize(25);
            bracket.setEditable(false);

            for (int c = 0; c < columns.size(); c++) {
                if (c == 0 || (dataTourStyle == 0 ? c == 1 : c == 2) || c == 3 || c == 4) {
                    TableColumn tc = new TableColumn();
                    tc = new TableColumn(columns.get(c));
                    final int index = c;
                    tc.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<String[], String>, ObservableValue<String>>() {
                        @Override
                        public ObservableValue<String> call(TableColumn.CellDataFeatures<String[], String> p) {
                            return new SimpleStringProperty((p.getValue()[index]));
                        }
                    });
                    bracket.getColumns().add(tc);
                }
            }

            if (dataTourStyle == 0) {
                for (int br = bracketNum*dataSets; br < bracketNum*dataSets+dataSets; br++) {
                    bracketData.add(playerData.get(br));
                }
            }else{
                int br = 0;
                for (int t = lastTeamInd; t < playerData.size(); t++) {
                    if (playerData.get(t)[2] != playerData.get(lastTeamInd)[2] || t == 0) {
                        bracketData.add(playerData.get(t));
                        lastTeamInd = t;
                        br++;
                    }
                    if (br >= dataSets) {
                        break;
                    }
                }
            }

            bracket.setItems(bracketData);

            bracket.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            bracket.setMinHeight(bracket.getFixedCellSize()*(bracket.getItems().size()+1));
            bracket.setMaxHeight(bracket.getFixedCellSize()*(bracket.getItems().size()+1));
            classVBox.getChildren().add(bracket);

            Text setLine = new Text("-------------------------------------------------------------------------------------------------------------------------------------");
            if (brackets.length > 0) {
                if (((bracketNum+1) % 2) == 0 && bracketNum+1 != brackets.length) {
                    classVBox.getChildren().add(setLine);
                }
            }

            bracketNum++;
        }
    }

    //Methods to add to pageBehavior


    //Use this inherited method to call all methods related to class needed for functionality
    @Override
    public void pageBehavior() {

    }
}

