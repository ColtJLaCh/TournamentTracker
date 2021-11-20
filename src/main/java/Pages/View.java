package Pages;

import HelpfulClasses.UsefulConstants;
import Nodes.PlayerData;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.css.Style;
import javafx.geometry.Insets;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Callback;

import java.lang.reflect.Array;
import java.util.ArrayList;

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

        Text tourTypeText = new Text("TYPE: " + (dataTourStyle == 0 ? "SINGLES TOURNAMENT" : "TEAMS TOURNAMENT"));

        classVBox = new VBox(tourTypeText); //Vbox needed for Top to Bottom layout, add assets here


        int bracketNum = 0;
        for (TableView bracket : brackets) {
            ObservableList<String[]> bracketData = FXCollections.observableArrayList();;

            bracket = new TableView();
            bracket.setMaxWidth(UsefulConstants.DEFAULT_SCREEN_WIDTH/3);
            bracket.setFixedCellSize(25);
            bracket.setEditable(false);

            for (int c = 0; c < columns.size(); c++) {
                if (c == 0 || (dataTourStyle == 0 ? c == 1 : c == 2)/*To show either player or team name*/ || c == 3 || c == 4) {
                    final int colNum = c;
                    TableColumn tc = new TableColumn(columns.get(c));
                    tc.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<String[], String>, ObservableValue<String>>() {
                        @Override
                        public ObservableValue<String> call(TableColumn.CellDataFeatures<String[], String> p) {
                            return new SimpleStringProperty((p.getValue()[colNum]));
                        }
                    });
                    bracket.getColumns().add(tc);
                }
            }

            int amntPerBracket = 0;
            if (dataTourStyle == 0) {
                amntPerBracket = dataPlayerArr.length / brackets.length;
            }else{
                amntPerBracket = uniqueTeams.size() / brackets.length;
            }

            for (int br = bracketNum*amntPerBracket; br < bracketNum*amntPerBracket+amntPerBracket; br++) {
                bracketData.add(playerData.get(br));
            }

            bracket.setItems(bracketData);

            bracket.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            bracket.setMinHeight(bracket.getFixedCellSize()*(bracket.getItems().size()+1));
            bracket.setMaxHeight(bracket.getFixedCellSize()*(bracket.getItems().size()+1));
            classVBox.getChildren().add(bracket);

            Text setLine = new Text("-------------------------------------------------------------------------------------------------------------------------------------");

            if (bracketNum % (int)Math.ceil(brackets.length/2) == 0 && dataSets > 1
                    && bracketNum >= brackets.length/2 && bracketNum < brackets.length-1) {
                classVBox.getChildren().add(setLine);
            }

            bracketNum++;
        }

        //classVBox.setAlignment(ALIGNMENT GOES HERE); //Usually Pos.TOP_LEFT
        classVBox.setPadding(new Insets(10,10,10,10)); //Set padding for Vbox (ORDER : double top, double right, double bottom, double left)
        classVBox.setSpacing(10); //Set spacing here

        classPane.setTop(classVBox);//Set it to top to place all content directly under menu
    }

    //Local methods
    private void dummyData() {
        //Fake table data, this will be injected into these variables when functionality is programmed

        //Singles
        dataTourName = "DummyTournamentSINGLES";
        dataStats = new String[] {"Wins", "Losses", "Time"};
        dataPlayerArr = new String[]{"Jesse","Jamie","Bobby","Billy","Brody","Mort"};
        dataTeamArr = new String[]{"NO TEAM","NO TEAM","NO TEAM","NO TEAM","NO TEAM","NO TEAM"};
        dataStatsArr = new String[dataPlayerArr.length][dataStats.length];
        dataSets = 2;


        //Teams
        /*
        dataTourName = "DummyTournamentTEAM";
        dataStats = new String[] {"Wins", "Losses", "Time"};
        dataPlayerArr = new String[]{"Jesse","Jamie","Bobby","Billy","Brody","Mort"};
        dataTeamArr = new String[]{"TEAM1","TEAM1","TEAM1","TEAM2","TEAM2","TEAM2"};
        dataStatsArr = new String[dataPlayerArr.length][dataStats.length];
        dataSets = 2;
        */

        //After table has been retrieved
        String lastTeam = "";
        for (String team : dataTeamArr) {
            if (team != "NO TEAM") dataTourStyle = 1;

            if (lastTeam != team) {
                uniqueTeams.add(team);
                lastTeam = team;
            }
        }

        //TODO: Change this to pull stat values from table
        for (int p = 0; p < dataPlayerArr.length; p++) {
            for (int s = 0; s < dataStats.length; s++) {
                dataStatsArr[p][s] = "0";
            }
        }

        int amntOfSets = dataSets;
        if (dataTourStyle == 0) {
            amntOfSets = (int)Math.ceil(dataPlayerArr.length/dataSets);
        }else{
            amntOfSets = (int)Math.ceil(dataTeamArr.length/dataSets);
        }

        brackets = new TableView[amntOfSets];


        playerData = FXCollections.observableArrayList();
        for (int t = 0; t < uniqueTeams.size(); t++) {
            for (int p = 0; p < dataPlayerArr.length; p++) {
                if (dataTourStyle == 0) {
                    String pId = String.valueOf(p);
                    PlayerData dataToAdd = new PlayerData(pId, dataPlayerArr[p], "NO TEAM", dataStatsArr[p]);
                    playerData.add(dataToAdd.getData());
                }else{
                    String tId = String.valueOf(t);
                    PlayerData dataToAdd = new PlayerData(tId, dataPlayerArr[p], uniqueTeams.get(t), dataStatsArr[p]);
                    playerData.add(dataToAdd.getData());
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

    //Methods to add to pageBehavior


    //Use this inherited method to call all methods related to class needed for functionality
    @Override
    public void pageBehavior() {

    }
}

